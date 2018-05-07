package com.scrabble.controller;

import com.scrabble.web.exception.ValidationFailedException;
import org.springframework.validation.BindingResult;

class BaseController {

    void validateInput(BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationFailedException(result);
        }
    }
}
