package com.kdv.catalogist.common.services.user.impl;

import com.kdv.catalogist.common.exception.ExceptionType;
import com.kdv.catalogist.common.exception.ExtException;
import com.kdv.catalogist.common.services.user.share.UserDto;
import com.kdv.catalogist.common.services.user.share.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jooq.codegen.tables.records.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserDBService userDBService;

  @Setter(onMethod_ = @Autowired)
  private PasswordEncoder passwordEncoder;

  @Override
  public List<UserDto> getAll() {
    return userDBService.getAll();
  }

  @Override
  public UserDto getByUsername(String username) {
    return userDBService.getByUsername(username)
        .orElseThrow(ExceptionType.USER_NOT_FOUND::exception);
  }

  @Override
  public UserDto getById(Long id) {
    return userDBService.getById(id)
        .orElseThrow(ExceptionType.USER_NOT_FOUND::exception);
  }

  @Override
  public List<UserDto> getAllById(Set<Long> userIds) {
    return userDBService.getAllById(userIds);
  }

  @Override
  public UserDto create(UserDto userDto) {
    if (userDBService.getByUsername(userDto.getUsername()).isPresent()) {
      throw ExtException.of(ExceptionType.USER_ALREADY_EXISTS)
          .arg("username", userDto.getUsername())
          .build();
    }

    UserRecord userRecord = userDBService.getNewRecord();

    userRecord.setUsername(userDto.getUsername());
    userRecord.setPassword(passwordEncoder.encode(userDto.getPassword()));

    userRecord = userDBService.create(userRecord);

    return getById(userRecord.getId());
  }

}
