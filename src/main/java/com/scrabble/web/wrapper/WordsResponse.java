package com.scrabble.web.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordsResponse {
    private String word;
    private Integer point;
}
