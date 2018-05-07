package com.scrabble.service;

import com.scrabble.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileDictionaryService implements DictionaryService {

    @Value("${dictionary.file.path}")
    private Resource dictionaryFile;

    private List<String> dictionary;

    @PostConstruct
    public void init() throws IOException {

        try (Stream<String> stream = Files.lines(Paths.get(dictionaryFile.getURI()))) {
           dictionary = stream.map(StringUtils::toUpperTurkish).collect(Collectors.toList());
        }
    }

    public Boolean isValid(String word) {
        return dictionary.contains(word);
    }
}
