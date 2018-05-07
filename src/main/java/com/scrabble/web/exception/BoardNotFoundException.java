package com.scrabble.web.exception;

public class BoardNotFoundException extends ApplicationException {
    public BoardNotFoundException(){
        super("Board not found or is not active");
    }
}
