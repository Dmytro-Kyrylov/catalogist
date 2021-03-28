package com.kdv.catalogist.mvc.controllers.services.list;

import com.kdv.catalogist.common.services.list.share.ListDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListMvcMapper {

  ListDto toInternalDto(ListMvcDto dto);

}
