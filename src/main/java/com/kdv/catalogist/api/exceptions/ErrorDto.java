package com.kdv.catalogist.api.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kdv.catalogist.common.exception.ExceptionType;
import com.kdv.catalogist.common.exception.ExtException;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto {

  private String type;

  private String message;

  private Map<String, String> args;

  public ErrorDto(ExtException exception) {
    this.type = exception.getType().toString();
    this.message = exception.getMessage();
    this.args = exception.getArgs();
  }

  public ErrorDto(String type, String message) {
    this.type = type;
    this.message = message;
  }

  public ResponseEntity<ErrorDto> responseEntity(ExceptionType exceptionType) {
    return new ResponseEntity<>(this, exceptionType.getHttpStatus());
  }

}
