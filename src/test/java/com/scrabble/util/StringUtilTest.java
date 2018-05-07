package com.scrabble.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StringUtilTest {

    @Test
    public void test_letters() {
        assertThat(StringUtils.letters("ABC"), is(new String[]{"A", "B", "C"}));
    }

    @Test
    public void test_toUpperTurkish() {
        assertThat(StringUtils.toUpperTurkish("ışçiüjö"), is("IŞÇİÜJÖ"));
        assertThat(StringUtils.toUpperTurkish("abCd"), is("ABCD"));
    }
}
