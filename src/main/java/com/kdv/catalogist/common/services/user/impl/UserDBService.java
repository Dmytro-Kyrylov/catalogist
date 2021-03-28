package com.kdv.catalogist.common.services.user.impl;

import com.kdv.catalogist.common.services.user.share.UserDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.codegen.Sequences;
import org.jooq.codegen.tables.records.UserRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.jooq.codegen.tables.User.USER;

@Repository
@RequiredArgsConstructor
public class UserDBService {

  private final DSLContext dsl;

  public Optional<UserDto> getByUsername(String username) {
    return dsl.selectFrom(USER)
        .where(USER.USERNAME.eq(username))
        .fetchOptionalInto(UserDto.class);
  }

  public Optional<UserDto> getById(Long id) {
    return dsl.selectFrom(USER)
        .where(USER.ID.eq(id))
        .fetchOptionalInto(UserDto.class);
  }

  public UserRecord getNewRecord() {
    return dsl.newRecord(USER);
  }

  public UserRecord create(UserRecord userRecord) {
    userRecord.setId(nextId());
    userRecord.insert();
    return userRecord;
  }

  public List<UserDto> getAll() {
    return dsl.selectFrom(USER).fetchInto(UserDto.class);
  }

  public List<UserDto> getAllById(Set<Long> userIds) {
    return dsl.selectFrom(USER)
        .where(USER.ID.in(userIds))
        .fetchInto(UserDto.class);
  }

  private Long nextId() {
    return dsl.nextval(Sequences.SEQ_USER);
  }

}
