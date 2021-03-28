package com.kdv.catalogist.api.exceptions;

import com.kdv.catalogist.common.exception.ExceptionType;
import com.kdv.catalogist.common.exception.ExtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestControllerAdvice("com.kdv.catalogist.api.services")
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ExtException.class)
  public ResponseEntity<ErrorDto> handleValidationException(ExtException exception) {
    return new ErrorDto(exception).responseEntity(exception.getType());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleValidationException(Exception exception) {
    log.error(exception.getMessage(), exception);
    return new ErrorDto(ExceptionType.UNKNOWN.exception()).responseEntity(ExceptionType.UNKNOWN);
  }

  @NonNull
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                @NonNull HttpHeaders headers,
                                                                @NonNull HttpStatus status,
                                                                @NonNull WebRequest request) {
    List<ValidationErrorDto> errors = new LinkedList<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      if (error instanceof FieldError) {
        String fieldName = ((FieldError) error).getField();
        Object value = ((FieldError) error).getRejectedValue();
        String errorMessage = error.getDefaultMessage();

        errors.add(ValidationErrorDto.of(errorMessage, value, fieldName));
      } else {
        errors.add(ValidationErrorDto.of(error.getDefaultMessage(), null, null));
      }
    });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @NonNull
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                      @NonNull HttpHeaders headers,
                                                      @NonNull HttpStatus status,
                                                      @NonNull WebRequest request) {
    ErrorDto errorDto = new ErrorDto(ExceptionType.BAD_REQUEST.name(), ex.getMessage());
    return new ResponseEntity<>(errorDto, ExceptionType.BAD_REQUEST.getHttpStatus());
  }

  @NonNull
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                @NonNull HttpHeaders headers,
                                                                @NonNull HttpStatus status,
                                                                @NonNull WebRequest request) {
    ErrorDto errorDto = new ErrorDto(ExceptionType.BAD_REQUEST.name(), ex.getMessage());
    return new ResponseEntity<>(errorDto, ExceptionType.BAD_REQUEST.getHttpStatus());
  }

  @NonNull
  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex,
                                                       @NonNull HttpHeaders headers,
                                                       @NonNull HttpStatus status,
                                                       @NonNull WebRequest request) {
    ErrorDto errorDto = new ErrorDto(ExceptionType.BAD_REQUEST.name(), ex.getMessage());
    return new ResponseEntity<>(errorDto, ExceptionType.BAD_REQUEST.getHttpStatus());
  }

}
