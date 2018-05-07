package com.scrabble;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestHelper {

    public static ResultMatcher jsonResult = content().contentType("application/json;charset=UTF-8");
    public static ResultMatcher textPlainResult = content().contentType("text/plain;charset=UTF-8");

    public static String body(String file) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(file).getInputStream(), Charset.forName("utf-8"));
    }

    public static RequestBuilder postJson(String target, String file) throws IOException {
        return post(target).content(body(file)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
}
