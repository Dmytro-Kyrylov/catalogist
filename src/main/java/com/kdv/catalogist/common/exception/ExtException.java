package com.kdv.catalogist.common.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.Map;

@Data
@Builder(builderMethodName = "innerBuilder")
@EqualsAndHashCode(callSuper = false)
public class ExtException extends RuntimeException {

  private final ExceptionType type;

  private String message;

  @Singular
  private Map<String, String> args;

  public static ExtExceptionBuilder of(ExceptionType type) {
    return innerBuilder().type(type);
  }

  public String getMessage() {
    if (message == null) {
      return type.getMessage();
    }
    return message;
  }

}
