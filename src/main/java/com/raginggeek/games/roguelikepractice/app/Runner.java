package com.raginggeek.games.roguelikepractice.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class Runner implements CommandLineRunner {
    private AppFrame frame;

    public Runner(AppFrame appFrame) {
        frame = appFrame;
    }

    @Override
    public void run(String... args) throws Exception {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
    }
}
