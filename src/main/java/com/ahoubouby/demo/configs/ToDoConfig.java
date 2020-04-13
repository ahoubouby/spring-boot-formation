package com.ahoubouby.demo.configs;
/*
 * ahoubouby created on 13/04/2020
 * E-MAIL: ahoubouby@gmail.com
 */

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

@Configuration
public class ToDoConfig {

    private Environment environment;

    ToDoConfig(Environment environment) {
        this.environment = environment;

    }

    @Bean
    @DependsOn("embeddedMongoServer")
    public MongoClient mongoClient() {
        int port = this.environment.getProperty("local.mongo.port", Integer.class);
        return new MongoClient("localhost", port);
    }
}
