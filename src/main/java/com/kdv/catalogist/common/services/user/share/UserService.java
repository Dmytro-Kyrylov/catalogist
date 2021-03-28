package com.kdv.catalogist.common.services.user.share;

import java.util.List;
import java.util.Set;

public interface UserService {

  List<UserDto> getAll();

  UserDto getByUsername(String username);

  UserDto getById(Long id);

  List<UserDto> getAllById(Set<Long> userIds);

  UserDto create(UserDto userDto);

}
