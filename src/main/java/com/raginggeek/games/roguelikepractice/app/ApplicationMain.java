package com.raginggeek.games.roguelikepractice.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ApplicationMain {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationMain.class).headless(false).run(args);
    }

}
