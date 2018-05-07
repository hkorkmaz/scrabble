package com.scrabble.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class EmbeddedDbConfig {

    @Resource
    private Environment env;

    @Bean("dataSource")
    @Profile({Profiles.TEST, Profiles.EMBEDDED})
    public DataSource embeddedDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
                .setName("com.scrabble;DATABASE_TO_UPPER=false;MODE=MySql")
                .build();
    }
}