package com.kdv.catalogist.common.services.list.share;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.kdv.catalogist.common.model.AuditedDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListDto extends AuditedDto {

  @NotBlank
  private String title;

  private String description;

  @JsonSetter(nulls = Nulls.AS_EMPTY)
  private Set<Long> relatedUserIds = new HashSet<>();

}
