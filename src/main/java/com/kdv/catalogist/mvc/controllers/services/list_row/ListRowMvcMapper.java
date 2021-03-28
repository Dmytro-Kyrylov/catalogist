package com.kdv.catalogist.mvc.controllers.services.list_row;

import com.kdv.catalogist.common.services.list_row.share.ListRowDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListRowMvcMapper {

  ListRowDto toInternalDto(ListRowMvcDto listRowMvcDto);

}
