package com.kdv.catalogist.common.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {

  UNKNOWN("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR),
  BAD_REQUEST("Bad request", HttpStatus.BAD_REQUEST),
  ACCESS_DENIED("Insufficient privileges", HttpStatus.FORBIDDEN),

  LIST_NOT_FOUND("List was not found", HttpStatus.NOT_FOUND),
  LIST_ROW_NOT_FOUND("List row was not found", HttpStatus.NOT_FOUND),

  USER_ALREADY_EXISTS("User already exists", HttpStatus.CONFLICT),
  USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND);

  @JsonValue
  private final String message;

  private final HttpStatus httpStatus;

  public ExtException exception() {
    return ExtException.of(this).build();
  }

}