package com.kdv.catalogist.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditableFields {

  ID("id"),
  CREATED_AT("created_at"),
  CREATED_BY("created_by"),
  UPDATED_AT("updated_at"),
  UPDATED_BY("updated_by");

  private final String sqlName;

}
