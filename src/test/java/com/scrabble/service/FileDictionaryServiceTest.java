package com.scrabble.service;

import com.scrabble.IntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FileDictionaryServiceTest extends IntegrationTest {

    @Autowired
    private FileDictionaryService dictionaryService;

    @Test
    public void test_isValid() {
        assertThat(dictionaryService.isValid("ADAM"), is(true));
        assertThat(dictionaryService.isValid("adam"), is(false));
        assertThat(dictionaryService.isValid("XXYY"), is(false));
    }
}
