package com.kdv.catalogist.common.services.list_row.impl;

import com.kdv.catalogist.common.services.list_row.share.ListRowDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.codegen.Sequences;
import org.jooq.codegen.tables.records.ListRowRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.jooq.codegen.tables.ListRow.LIST_ROW;

@Repository
@RequiredArgsConstructor
public class ListRowDBService {

  private final DSLContext dsl;

  public List<ListRowDto> getAll(Long listId) {
    return dsl.selectFrom(LIST_ROW)
        .where(LIST_ROW.LIST_ID.eq(listId))
        .orderBy(LIST_ROW.CREATED_BY.desc())
        .fetchInto(ListRowDto.class);
  }

  public Optional<ListRowDto> getById(Long listId, Long listRowId) {
    return dsl.selectFrom(LIST_ROW)
        .where(LIST_ROW.ID.eq(listRowId).and(LIST_ROW.LIST_ID.eq(listId)))
        .fetchOptionalInto(ListRowDto.class);
  }

  public ListRowRecord create(ListRowRecord listRowRecord) {
    listRowRecord.setId(nextId());
    listRowRecord.insert();
    return listRowRecord;
  }

  public ListRowRecord update(ListRowRecord listRowRecord) {
    listRowRecord.update();
    return listRowRecord;
  }

  public int delete(Long listId, Long listRowId) {
    return dsl.deleteFrom(LIST_ROW)
        .where(LIST_ROW.ID.eq(listRowId).and(LIST_ROW.LIST_ID.eq(listId)))
        .execute();
  }

  public void deleteAll(Long listId) {
    dsl.deleteFrom(LIST_ROW)
        .where(LIST_ROW.LIST_ID.eq(listId))
        .execute();
  }

  public ListRowRecord getNewRecord() {
    return dsl.newRecord(LIST_ROW);
  }

  public Optional<ListRowRecord> getRecordById(Long listId, Long listRowId) {
    return dsl.selectFrom(LIST_ROW).where(LIST_ROW.ID.eq(listRowId).and(LIST_ROW.LIST_ID.eq(listId))).fetchOptional();
  }

  private Long nextId() {
    return dsl.nextval(Sequences.SEQ_LIST_ROW);
  }

}
