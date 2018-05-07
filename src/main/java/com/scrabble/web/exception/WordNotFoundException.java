package com.scrabble.web.exception;

import java.util.List;

public class WordNotFoundException extends ApplicationException{
    public WordNotFoundException(List<String> words) {
        super(String.format("Words can not be found in dictionary %s", words));
    }
}
