package com.kdv.catalogist.common.services.list.share;

import java.util.List;

public interface ListService {

  List<ListDto> getAllByUserId(Long userId);

  List<ListDto> getAll();

  ListDto get(Long id);

  ListDto create(ListDto listDto);

  ListDto updateWithoutUserChange(Long id, ListDto listDto);

  void delete(Long id);

}
