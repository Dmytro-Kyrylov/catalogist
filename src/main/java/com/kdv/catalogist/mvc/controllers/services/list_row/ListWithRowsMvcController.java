package com.kdv.catalogist.mvc.controllers.services.list_row;

import com.kdv.catalogist.common.services.list.share.ListDto;
import com.kdv.catalogist.common.services.list.share.ListService;
import com.kdv.catalogist.common.services.list_row.share.ListRowDto;
import com.kdv.catalogist.common.services.list_row.share.ListRowService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ListWithRowsMvcController {

  private final ListRowMvcMapper listRowMvcMapper;

  private final UserService userService;

  @Setter(onMethod_ = {@Autowired, @Qualifier("listServiceImpl")})
  private ListService listService;

  @Setter(onMethod_ = {@Autowired, @Qualifier("securedListRowServiceImpl")})
  private ListRowService listRowService;

  @GetMapping(ListWithRowsMvcUrls.FIND)
  public String find(@PathVariable Long listId, Model model) {
    ListDto listDto = listService.get(listId);
    List<ListRowDto> listRows = listRowService.find(listId);
    String usersWithAccess = userService.getAllById(listDto.getRelatedUserIds())
        .stream()
        .map(UserDto::getUsername)
        .collect(Collectors.joining(", "));

    model.addAttribute("list", listDto);
    model.addAttribute("usersWithAccess", usersWithAccess);
    model.addAttribute("listRows", listRows);
    return "list_with_rows";
  }

  @PostMapping(ListWithRowsMvcUrls.CREATE)
  public String create(@PathVariable Long listId, @Valid @ModelAttribute ListRowMvcDto dto,
                       RedirectAttributes redirectAttributes) {
    listRowService.create(listId, listRowMvcMapper.toInternalDto(dto));
    redirectAttributes.addAttribute("listId", listId);
    return "redirect:" + ListWithRowsMvcUrls.FIND;
  }

  @PostMapping(ListWithRowsMvcUrls.DELETE)
  public String delete(@PathVariable Long listId, @PathVariable Long listRowId, RedirectAttributes redirectAttributes) {
    listRowService.delete(listId, listRowId);
    redirectAttributes.addAttribute("listId", listId);
    return "redirect:" + ListWithRowsMvcUrls.FIND;
  }

  @ModelAttribute("listRow")
  public ListRowMvcDto emptyListRow() {
    return new ListRowMvcDto();
  }

}
