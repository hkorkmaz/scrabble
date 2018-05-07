package com.scrabble.web.exception;

public class TileNotAvailableException extends ApplicationException {
    public TileNotAvailableException() {
        super("Tile is not empty or is out of board");
    }
}
