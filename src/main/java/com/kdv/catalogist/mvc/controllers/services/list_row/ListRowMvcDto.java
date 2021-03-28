package com.kdv.catalogist.mvc.controllers.services.list_row;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class ListRowMvcDto {

  @NotBlank
  private String title;

  private String content;

  @JsonSetter(nulls = Nulls.AS_EMPTY)
  private List<@NotBlank String> links = new ArrayList<>();

}
