package com.kdv.catalogist.api.services.list;

import com.kdv.catalogist.common.beans.RequestContext;
import com.kdv.catalogist.common.services.list.share.ListDto;
import com.kdv.catalogist.common.services.list.share.ListService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ListRestController {

  @Setter(onMethod_ = {@Autowired, @Qualifier("securedListServiceImpl")})
  private ListService listService;

  @Setter(onMethod_ = @Autowired)
  private RequestContext requestContext;

  @GetMapping(ListRestUrls.GET)
  public ListDto get(@PathVariable Long listId) {
    return listService.get(listId);
  }

  @GetMapping(ListRestUrls.FIND)
  public List<ListDto> find() {
    return listService.getAll();
  }

  @PostMapping(ListRestUrls.CREATE)
  @ResponseStatus(HttpStatus.CREATED)
  public ListDto create(@Valid @RequestBody ListDto dto) {
    requestContext.getCurrentUser().ifPresent(x -> dto.getRelatedUserIds().add(x.getId()));
    return listService.create(dto);
  }

  @PutMapping(ListRestUrls.UPDATE)
  public ListDto update(@PathVariable Long listId, @Valid @RequestBody ListDto dto) {
    return listService.updateWithoutUserChange(listId, dto);
  }

  @DeleteMapping(ListRestUrls.DELETE)
  public void delete(@PathVariable Long listId) {
    listService.delete(listId);
  }

}
