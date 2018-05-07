package com.scrabble.web.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class ValidationFailedException extends RuntimeException {

    private BindingResult bindingResult;

    public ValidationFailedException(BindingResult bindingResult){
        this.bindingResult = bindingResult;
    }
}
