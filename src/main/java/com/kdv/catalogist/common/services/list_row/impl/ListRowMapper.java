package com.kdv.catalogist.common.services.list_row.impl;

import com.kdv.catalogist.common.services.list_row.share.ListRowDto;
import org.jooq.codegen.tables.records.ListRowRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListRowMapper {

  ListRowDto toDto(ListRowRecord listRowRecord);

}
