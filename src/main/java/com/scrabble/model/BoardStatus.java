package com.scrabble.model;

import com.scrabble.util.jpa.EnumConverter;
import com.scrabble.util.jpa.PersistableEnum;

public enum BoardStatus implements PersistableEnum<Integer> {
    INITIAL(-1),
    PASSIVE(0),
    ACTIVE(1);

    private final Integer value;

    BoardStatus(Integer value){
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static class Converter extends EnumConverter<BoardStatus, Integer> {
        public Converter() {
            super(BoardStatus.class);
        }
    }
}
