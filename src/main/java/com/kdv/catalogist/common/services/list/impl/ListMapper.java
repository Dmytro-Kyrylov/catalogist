package com.kdv.catalogist.common.services.list.impl;

import com.kdv.catalogist.common.services.list.share.ListDto;
import org.jooq.codegen.tables.records.ListRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListMapper {

  ListDto toDto(ListRecord listRecord);

}
