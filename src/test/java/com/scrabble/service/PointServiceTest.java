package com.scrabble.service;

import com.scrabble.IntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PointServiceTest extends IntegrationTest {

    @Autowired
    private PointService pointService;

    @Test
    public void test_pointOf() {
        assertThat(pointService.pointOf("ABCDEFŞİĞÜÇÖI"), is(48));
    }
}
