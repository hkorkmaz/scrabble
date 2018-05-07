package com.scrabble.util;

import java.util.Locale;

public class StringUtils {

    private static Locale localeTr = Locale.forLanguageTag("tr-TR");

    public static String[] letters(String str){
        return str.chars()
                .mapToObj(c -> Character.toString((char) c))
                .toArray(String[]::new);
    }

    public static String toUpperTurkish(String str){
        return str.toUpperCase(localeTr);
    }
}
