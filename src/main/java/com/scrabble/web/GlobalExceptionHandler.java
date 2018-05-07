package com.scrabble.web;

import com.scrabble.web.exception.ValidationFailedException;
import com.scrabble.web.wrapper.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<Result> handleValidationException(ValidationFailedException exception) {
        List<String> messages = exception.getBindingResult()
                .getFieldErrors().stream()
                .map(fieldError -> String.join(" ", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return Result.Error(BAD_REQUEST).add("errors", messages).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleServlet(Exception exception) {
        log.error("Exception while processing request ", exception);
        return Result.Error(INTERNAL_SERVER_ERROR).message("Something went wrong").build();
    }
}
