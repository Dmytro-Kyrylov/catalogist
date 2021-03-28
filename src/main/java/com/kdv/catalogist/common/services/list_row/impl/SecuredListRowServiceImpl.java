package com.kdv.catalogist.common.services.list_row.impl;

import com.kdv.catalogist.common.services.list.share.ListService;
import com.kdv.catalogist.common.services.list_row.share.ListRowDto;
import com.kdv.catalogist.common.services.list_row.share.ListRowService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecuredListRowServiceImpl implements ListRowService {

  @Setter(onMethod_ = {@Autowired, @Qualifier("securedListServiceImpl")})
  private ListService listService;

  private final ListRowService listRowService;

  @Override
  public ListRowDto get(Long listId, Long listRowId) {
    listService.get(listId);
    return listRowService.get(listId, listRowId);
  }

  @Override
  public List<ListRowDto> find(Long listId) {
    listService.get(listId);
    return listRowService.find(listId);
  }

  @Override
  public ListRowDto create(Long listId, ListRowDto dto) {
    listService.get(listId);
    return listRowService.create(listId, dto);
  }

  @Override
  public ListRowDto update(Long listId, Long listRowId, ListRowDto dto) {
    listService.get(listId);
    return listRowService.update(listId, listRowId, dto);
  }

  @Override
  public void delete(Long listId, Long listRowId) {
    listService.get(listId);
    listRowService.delete(listId, listRowId);
  }

  @Override
  public void deleteAll(Long listId) {
    throw new UnsupportedOperationException();
  }

}
