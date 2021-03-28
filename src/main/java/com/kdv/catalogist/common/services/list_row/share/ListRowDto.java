package com.kdv.catalogist.common.services.list_row.share;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.kdv.catalogist.common.model.AuditedDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListRowDto extends AuditedDto {

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY, readOnly = true)
  private Long listId;

  @NotBlank
  private String title;

  private String content;

  @JsonSetter(nulls = Nulls.AS_EMPTY)
  private Set<@NotBlank String> links = new HashSet<>();

}
