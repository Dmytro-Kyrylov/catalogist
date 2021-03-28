package com.kdv.catalogist.mvc.controllers.services.list;

import com.kdv.catalogist.common.beans.RequestContext;
import com.kdv.catalogist.common.exception.ExceptionType;
import com.kdv.catalogist.common.services.list.share.ListDto;
import com.kdv.catalogist.common.services.list.share.ListService;
import com.kdv.catalogist.common.services.user.share.UserDto;
import com.kdv.catalogist.common.services.user.share.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ListMvcController {

  private final ListMvcMapper listMvcMapper;

  private final UserService userService;

  @Setter(onMethod_ = @Autowired)
  private RequestContext requestContext;

  @Setter(onMethod_ = {@Autowired, @Qualifier("securedListServiceImpl")})
  private ListService listService;

  @GetMapping(ListMvcUrls.FIND)
  public String find(Model model) {
    List<ListDto> listDtoList = listService.getAll();
    model.addAttribute("lists", listDtoList);
    return "lists";
  }

  @GetMapping(ListMvcUrls.CREATE)
  public String getCreate(Model model) {
    List<UserDto> users = userService.getAll().stream()
        .filter(x -> !x.getId().equals(
            requestContext.getCurrentUser()
                .map(UserDto::getId)
                .orElseThrow(ExceptionType.USER_NOT_FOUND::exception)
            )
        )
        .collect(Collectors.toList());

    model.addAttribute("users", users);
    model.addAttribute("list", new ListMvcDto());
    return "list_form";
  }

  @PostMapping(ListMvcUrls.CREATE)
  public String create(@Valid @ModelAttribute ListMvcDto dto) {
    requestContext.getCurrentUser().ifPresent(x -> dto.getRelatedUserIds().add(x.getId()));
    listService.create(listMvcMapper.toInternalDto(dto));
    return "redirect:" + ListMvcUrls.FIND;
  }

  @GetMapping(ListMvcUrls.UPDATE)
  public String getUpdate(@PathVariable Long listId, Model model) {
    ListDto listDto = listService.get(listId);
    model.addAttribute("list", listDto);
    return "list_form";
  }

  @PostMapping(ListMvcUrls.UPDATE)
  public String update(@PathVariable Long listId, @Valid @ModelAttribute ListDto dto) {
    listService.updateWithoutUserChange(listId, dto);
    return "redirect:" + ListMvcUrls.FIND;
  }

  @PostMapping(ListMvcUrls.DELETE)
  public String delete(@PathVariable Long listId) {
    listService.delete(listId);
    return "redirect:" + ListMvcUrls.FIND;
  }

}
