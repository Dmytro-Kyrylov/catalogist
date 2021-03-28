package com.kdv.catalogist.common.services.list_row.share;

import java.util.List;

public interface ListRowService {

  List<ListRowDto> find(Long listId);

  ListRowDto get(Long listId, Long id);

  ListRowDto create(Long listId, ListRowDto listRowDto);

  ListRowDto update(Long listId, Long id, ListRowDto listRowDto);

  void delete(Long listId, Long id);

  void deleteAll(Long listId);

}
