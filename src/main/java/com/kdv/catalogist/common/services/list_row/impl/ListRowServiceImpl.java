package com.kdv.catalogist.common.services.list_row.impl;

import com.kdv.catalogist.common.beans.RequestContext;
import com.kdv.catalogist.common.exception.ExceptionType;
import com.kdv.catalogist.common.services.list_row.share.ListRowDto;
import com.kdv.catalogist.common.services.list_row.share.ListRowService;
import com.kdv.catalogist.common.services.user.share.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jooq.codegen.tables.records.ListRowRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListRowServiceImpl implements ListRowService {

  private final ListRowDBService listRowDBService;

  private final ListRowMapper listRowMapper;

  @Setter(onMethod_ = @Autowired)
  private RequestContext requestContext;

  @Override
  public List<ListRowDto> find(Long listId) {
    return listRowDBService.getAll(listId);
  }

  @Override
  public ListRowDto get(Long listId, Long id) {
    return listRowDBService.getById(listId, id)
        .orElseThrow(ExceptionType.LIST_ROW_NOT_FOUND::exception);
  }

  @Override
  public ListRowDto create(Long listId, ListRowDto listRowDto) {
    ListRowRecord listRowRecord = listRowDBService.getNewRecord();

    listRowRecord.setTitle(listRowDto.getTitle());
    listRowRecord.setContent(listRowDto.getContent());
    listRowRecord.setLinks(listRowDto.getLinks().toArray(new String[0]));
    listRowRecord.setListId(listId);

    LocalDateTime now = LocalDateTime.now();
    listRowRecord.setCreatedAt(now);
    listRowRecord.setUpdatedAt(now);

    listRowRecord.setCreatedBy(requestContext.getCurrentUser().map(UserDto::getId).orElse(null));
    listRowRecord.setUpdatedBy(requestContext.getCurrentUser().map(UserDto::getId).orElse(null));

    return listRowMapper.toDto(listRowDBService.create(listRowRecord));
  }

  @Override
  public ListRowDto update(Long listId, Long id, ListRowDto listRowDto) {
    ListRowRecord listRowRecord = listRowDBService.getRecordById(listId, id)
        .orElseThrow(ExceptionType.LIST_ROW_NOT_FOUND::exception);

    listRowRecord.setTitle(listRowDto.getTitle());
    listRowRecord.setContent(listRowDto.getContent());
    listRowRecord.setLinks(listRowDto.getLinks().toArray(new String[0]));

    LocalDateTime now = LocalDateTime.now();
    listRowRecord.setUpdatedAt(now);

    listRowRecord.setUpdatedBy(requestContext.getCurrentUser().map(UserDto::getId).orElse(null));

    return listRowMapper.toDto(listRowDBService.update(listRowRecord));
  }

  @Override
  public void delete(Long listId, Long id) {
    if (listRowDBService.delete(listId, id) == 0) {
      throw ExceptionType.LIST_ROW_NOT_FOUND.exception();
    }
  }

  @Override
  public void deleteAll(Long listId) {
    listRowDBService.deleteAll(listId);
  }

}
