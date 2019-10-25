package com.raginggeek.games.roguelikepractice.screens;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.service.LevelUpService;

import java.awt.event.KeyEvent;
import java.util.List;

public class LevelUpScreen implements Screen {
    private LevelUpService levelUpService;
    private Creature player;
    private int picks;

    public LevelUpScreen(Creature player, int picks) {
        this.levelUpService = new LevelUpService();
        this.player = player;
        this.picks = picks;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        List<String> options = levelUpService.getLevelUpOptions();

        int y = 5;
        terminal.clear(' ', 5, y, 30, options.size() + 2);
        terminal.write("     Choose a level up bonus     ", 5, y++);
        terminal.write("-----------------------------------", 5, y++);
        for (int i = 0; i < options.size(); i++) {
            terminal.write(String.format("[%d] %s", i + 1, options.get(i)), 5, y++);
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        List<String> options = levelUpService.getLevelUpOptions();
        String chars = "";
        for (int i = 0; i < options.size(); i++) {
            chars = chars + (i + 1);
        }

        int i = chars.indexOf(key.getKeyChar());
        if (i < 0) {
            return this;
        }
        levelUpService.getLevelUpOption(options.get(i)).invoke(player);

        if (--picks < 1) {
            return null;
        } else {
            return this;
        }
    }
}
