package com.scrabble.web.exception;

public class InvalidPositionException extends ApplicationException {
    public InvalidPositionException() {
        super("Invalid position");
    }
}
