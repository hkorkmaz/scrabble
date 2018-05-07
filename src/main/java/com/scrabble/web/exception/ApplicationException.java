package com.scrabble.web.exception;

public class ApplicationException extends RuntimeException {
    ApplicationException(String message){
        super(message);
    }
}
