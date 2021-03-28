package com.kdv.catalogist.common.services.list.impl;

import com.kdv.catalogist.common.beans.RequestContext;
import com.kdv.catalogist.common.exception.ExceptionType;
import com.kdv.catalogist.common.services.list.share.ListDto;
import com.kdv.catalogist.common.services.list.share.ListService;
import com.kdv.catalogist.common.services.list_row.share.ListRowService;
import com.kdv.catalogist.common.services.user.share.UserDto;
import com.kdv.catalogist.common.services.user.share.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jooq.codegen.tables.records.ListRecord;
import org.jooq.codegen.tables.records.ListUserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListServiceImpl implements ListService {

  private final ListDBService listDBService;

  private final UserService userService;

  private final ListMapper listMapper;

  @Setter(onMethod_ = {@Autowired, @Qualifier("listRowServiceImpl")})
  private ListRowService listRowService;

  @Setter(onMethod_ = @Autowired)
  private RequestContext requestContext;

  @Override
  public List<ListDto> getAllByUserId(Long userId) {
    return listDBService.getAllByUserId(userId);
  }

  @Override
  public List<ListDto> getAll() {
    return listDBService.getAll();
  }

  @Override
  public ListDto get(Long id) {
    return listDBService.getById(id).orElseThrow(ExceptionType.LIST_NOT_FOUND::exception);
  }

  @Override
  public ListDto create(ListDto listDto) {
    listDto.getRelatedUserIds().forEach(userService::getById);

    ListRecord listRecord = listDBService.getNewRecord();

    listRecord.setTitle(listDto.getTitle());
    listRecord.setDescription(listDto.getDescription());

    LocalDateTime now = LocalDateTime.now();
    listRecord.setCreatedAt(now);
    listRecord.setUpdatedAt(now);

    listRecord.setCreatedBy(requestContext.getCurrentUser().map(UserDto::getId).orElse(null));
    listRecord.setUpdatedBy(requestContext.getCurrentUser().map(UserDto::getId).orElse(null));

    ListDto result = listMapper.toDto(listDBService.create(listRecord, listDto.getRelatedUserIds()));
    result.setRelatedUserIds(listDto.getRelatedUserIds());

    return result;
  }

  @Override
  public ListDto updateWithoutUserChange(Long id, ListDto listDto) {
    ListRecord listRecord = listDBService.getRecordById(id).orElseThrow(ExceptionType.LIST_NOT_FOUND::exception);

    listRecord.setTitle(listDto.getTitle());
    listRecord.setDescription(listDto.getDescription());

    LocalDateTime now = LocalDateTime.now();
    listRecord.setUpdatedAt(now);

    listRecord.setUpdatedBy(requestContext.getCurrentUser().map(UserDto::getId).orElse(null));

    listDto = listMapper.toDto(listDBService.update(listRecord));
    listDto.setRelatedUserIds(
        listDBService.getListUserRecordsByListId(id)
            .stream()
            .map(ListUserRecord::getUserId)
            .collect(Collectors.toSet())
    );

    return listDto;
  }

  @Transactional
  @Override
  public void delete(Long id) {
    listRowService.deleteAll(id);
    if (listDBService.delete(id) == 0) {
      throw ExceptionType.LIST_NOT_FOUND.exception();
    }
  }

}
