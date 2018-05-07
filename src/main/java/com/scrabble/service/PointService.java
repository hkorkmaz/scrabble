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
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PointService {

    @Value("${points.file.path}")
    private Resource pointsFile;

    private Map<String, Integer> pointsMap;

    @PostConstruct
    public void init() throws IOException {

        try (Stream<String> stream = Files.lines(Paths.get(pointsFile.getURI()))) {
            pointsMap = stream.map(line -> line.split(","))
                    .collect(Collectors.toMap(k -> k[0], v -> Integer.valueOf(v[1])));
        }
    }

    public Integer pointOf(String word) {
        String[] letters = StringUtils.letters(word);

        return Arrays.stream(letters)
                .mapToInt(l -> pointsMap.get(l))
                .sum();
    }
}
