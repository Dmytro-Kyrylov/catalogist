package com.kdv.catalogist.common.services.list.impl;

import com.kdv.catalogist.common.services.list.share.ListDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.codegen.Sequences;
import org.jooq.codegen.tables.records.ListRecord;
import org.jooq.codegen.tables.records.ListUserRecord;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.jooq.codegen.tables.List.LIST;
import static org.jooq.codegen.tables.ListUser.LIST_USER;

@Repository
@RequiredArgsConstructor
public class ListDBService {

  private final DSLContext dsl;

  public List<ListDto> getAllByUserId(Long userId) {
    return dsl.select(getFieldsForListDto())
        .from(LIST)
        .innerJoin(LIST_USER).onKey()
        .where(LIST_USER.USER_ID.eq(userId))
        .groupBy(LIST.ID, LIST.CREATED_AT)
        .orderBy(LIST.CREATED_AT.desc())
        .fetchInto(ListDto.class);
  }

  public Optional<ListDto> getById(Long listId) {
    return dsl.select(getFieldsForListDto())
        .from(LIST)
        .innerJoin(LIST_USER).onKey()
        .where(LIST.ID.eq(listId))
        .groupBy(LIST.ID, LIST.CREATED_AT)
        .orderBy(LIST.CREATED_AT.desc())
        .fetchOptionalInto(ListDto.class);
  }

  public List<ListDto> getAll() {
    return dsl.select(getFieldsForListDto())
        .from(LIST)
        .innerJoin(LIST_USER).onKey()
        .groupBy(LIST.ID, LIST.CREATED_AT)
        .orderBy(LIST.CREATED_AT.desc())
        .fetchInto(ListDto.class);
  }

  public List<ListUserRecord> getListUserRecordsByListId(Long listId) {
    return dsl.selectFrom(LIST_USER).where(LIST_USER.LIST_ID.eq(listId)).fetch();
  }

  public ListRecord getNewRecord() {
    return dsl.newRecord(LIST);
  }

  public Optional<ListRecord> getRecordById(Long listId) {
    return dsl.selectFrom(LIST).where(LIST.ID.eq(listId)).fetchOptional();
  }

  @Transactional
  public ListRecord create(ListRecord listRecord, Set<Long> relatedUserIds) {
    listRecord.setId(nextId());

    listRecord.insert();

    addUsersToList(relatedUserIds, listRecord.getId());

    return listRecord;
  }

  private void addUsersToList(Set<Long> userIds, Long listId) {
    var insert = dsl.insertInto(LIST_USER, LIST_USER.LIST_ID, LIST_USER.USER_ID);
    for (Long relatedUserId : userIds) {
      insert = insert.values(listId, relatedUserId);
    }
    insert.execute();
  }

  public ListRecord update(ListRecord listRecord) {
    listRecord.update();
    return listRecord;
  }

  @Transactional
  public int delete(Long id) {
    dsl.deleteFrom(LIST_USER)
        .where(LIST_USER.LIST_ID.eq(id))
        .execute();
    return dsl.deleteFrom(LIST)
        .where(LIST.ID.eq(id))
        .execute();
  }

  private Long nextId() {
    return dsl.nextval(Sequences.SEQ_LIST);
  }

  private List<Field<?>> getFieldsForListDto() {
    List<Field<?>> fields = new ArrayList<>(Arrays.asList(LIST.fields()));
    fields.add(DSL.arrayAgg(LIST_USER.USER_ID).as("relatedUserIds"));
    return fields;
  }

}
