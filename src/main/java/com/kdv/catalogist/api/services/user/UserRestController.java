package com.kdv.catalogist.api.services.user;

import com.kdv.catalogist.common.services.user.share.UserDto;
import com.kdv.catalogist.common.services.user.share.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserRestController {

  private final UserService userService;

  @GetMapping(UserRestUrls.FIND)
  public List<UserDto> find() {
    return userService.getAll()
        .stream()
        .peek(x -> x.setPassword(null))
        .collect(Collectors.toList());
  }

  @PostMapping(UserRestUrls.CREATE)
  public UserDto create(@Valid @RequestBody UserDto userDto) {
    return userService.create(userDto);
  }

}
