package com.kdv.catalogist.mvc.controllers.services.list;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class ListMvcDto {

  private Long id;

  @NotBlank
  private String title;

  private String description;

  @JsonSetter(nulls = Nulls.AS_EMPTY)
  private List<Long> relatedUserIds = new ArrayList<>();

}
