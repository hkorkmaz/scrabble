package com.scrabble.model;

import com.scrabble.util.jpa.EnumConverter;
import com.scrabble.util.jpa.PersistableEnum;

public enum Direction implements PersistableEnum<String> {
    VERTICAL("V"),
    HORIZONTAL("H");

    private final String value;

    Direction(String value){
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static class Converter extends EnumConverter<Direction, String> {
        public Converter() {
            super(Direction.class);
        }
    }
}
