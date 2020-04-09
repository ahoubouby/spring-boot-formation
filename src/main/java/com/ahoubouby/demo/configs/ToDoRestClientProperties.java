package com.ahoubouby.demo.configs;
/*
 * abdelwahed created on 07/04/2020
 * E-MAI: ahoubouby@gmail.com
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "todo")
public class ToDoRestClientProperties {
    private String url;
    private String basePath;
}
