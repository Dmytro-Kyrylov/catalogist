package com.kdv.catalogist.common.services.list.impl;

import com.kdv.catalogist.common.beans.RequestContext;
import com.kdv.catalogist.common.exception.ExceptionType;
import com.kdv.catalogist.common.services.list.share.ListDto;
import com.kdv.catalogist.common.services.list.share.ListService;
import com.kdv.catalogist.common.services.user.share.UserDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecuredListServiceImpl implements ListService {

  @Setter(onMethod_ = {@Autowired, @Qualifier("listServiceImpl")})
  private ListService listService;

  @Setter(onMethod_ = @Autowired)
  private RequestContext requestContext;

  @Override
  public List<ListDto> getAllByUserId(Long userId) {
    if (requestContext.getCurrentUser()
        .filter(x -> x.getId().equals(userId))
        .isEmpty()) {
      throw ExceptionType.ACCESS_DENIED.exception();
    }

    return listService.getAllByUserId(userId);
  }

  @Override
  public List<ListDto> getAll() {
    UserDto userDto = requestContext.getCurrentUser().orElseThrow(ExceptionType.USER_NOT_FOUND::exception);
    return listService.getAllByUserId(userDto.getId());
  }

  @Override
  public ListDto get(Long id) {
    UserDto userDto = requestContext.getCurrentUser().orElseThrow(ExceptionType.USER_NOT_FOUND::exception);
    ListDto listDto = listService.get(id);
    if (!listDto.getRelatedUserIds().contains(userDto.getId())) {
      throw ExceptionType.ACCESS_DENIED.exception();
    }
    return listDto;
  }

  @Override
  public ListDto create(ListDto listDto) {
    return listService.create(listDto);
  }

  @Override
  public ListDto updateWithoutUserChange(Long id, ListDto listDto) {
    get(id);
    return listService.updateWithoutUserChange(id, listDto);
  }

  @Override
  public void delete(Long id) {
    get(id);
    listService.delete(id);
  }

}
