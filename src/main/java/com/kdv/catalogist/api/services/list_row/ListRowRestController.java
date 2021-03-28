package com.kdv.catalogist.api.services.list_row;

import com.kdv.catalogist.common.services.list_row.share.ListRowDto;
import com.kdv.catalogist.common.services.list_row.share.ListRowService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ListRowRestController {

  @Setter(onMethod_ = {@Autowired, @Qualifier("securedListRowServiceImpl")})
  private ListRowService listRowService;

  @GetMapping(ListRowRestUrls.GET)
  public ListRowDto get(@PathVariable Long listId, @PathVariable Long listRowId) {
    return listRowService.get(listId, listRowId);
  }

  @GetMapping(ListRowRestUrls.FIND)
  public List<ListRowDto> find(@PathVariable Long listId) {
    return listRowService.find(listId);
  }

  @PostMapping(ListRowRestUrls.CREATE)
  @ResponseStatus(HttpStatus.CREATED)
  public ListRowDto create(@PathVariable Long listId, @Valid @RequestBody ListRowDto dto) {
    return listRowService.create(listId, dto);
  }

  @PutMapping(ListRowRestUrls.UPDATE)
  public ListRowDto update(@PathVariable Long listId, @PathVariable Long listRowId,
                           @Valid @RequestBody ListRowDto dto) {
    return listRowService.update(listId, listRowId, dto);
  }

  @DeleteMapping(ListRowRestUrls.DELETE)
  public void delete(@PathVariable Long listId, @PathVariable Long listRowId) {
    listRowService.delete(listId, listRowId);
  }

}
