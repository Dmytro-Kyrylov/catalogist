package com.kdv.catalogist.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ValidationErrorDto {

  private String message;

  private Object value;

  private String field;

}
