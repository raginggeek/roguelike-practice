package com.raginggeek.games.roguelikepractice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "world")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorldConfig {
    private int height;
    private int width;
    private int depth;
}
