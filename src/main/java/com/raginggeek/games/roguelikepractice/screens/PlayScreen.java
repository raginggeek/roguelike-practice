package com.raginggeek.games.roguelikepractice.screens;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.actors.Creature;
import com.raginggeek.games.roguelikepractice.actors.CreatureFactory;
import com.raginggeek.games.roguelikepractice.actors.FieldOfView;
import com.raginggeek.games.roguelikepractice.world.World;
import com.raginggeek.games.roguelikepractice.world.WorldBuilder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {
    private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;
    private FieldOfView fov;

    public PlayScreen() {
        screenWidth = 80;
        screenHeight = 21;
        messages = new ArrayList<>();
        createWorld();
        CreatureFactory creatureFactory = new CreatureFactory(world);
        createCreatures(creatureFactory);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();
        displayTiles(terminal, left, top);
        displayCreatures(terminal, left, top);
        terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 22);
        String stats = String.format(" %3d/%3d hp", player.getHp(), player.getMaxHp());
        displayMessages(terminal, messages);
        terminal.write(stats, 1, 23);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                player.moveBy(-1, 0, 0);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                player.moveBy(1, 0, 0);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K:
                player.moveBy(0, -1, 0);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J:
                player.moveBy(0, 1, 0);
                break;
            case KeyEvent.VK_Y:
                player.moveBy(-1, -1, 0);
                break;
            case KeyEvent.VK_U:
                player.moveBy(1, -1, 0);
                break;
            case KeyEvent.VK_B:
                player.moveBy(-1, 1, 0);
                break;
            case KeyEvent.VK_N:
                player.moveBy(1, 1, 0);
                break;
            case KeyEvent.VK_ESCAPE:
                return new LoseScreen();
            case KeyEvent.VK_ENTER:
                return new WinScreen();
        }
        switch (key.getKeyChar()) {
            case '<':
                player.moveBy(0, 0, -1);
                break;
            case '>':
                player.moveBy(0, 0, 1);
                break;
        }
        world.update();
        return this;
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.getX() - screenWidth / 2, world.getWidth() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.getY() - screenHeight / 2, world.getHeight() - screenHeight));
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        fov.update(player.getX(), player.getY(), player.getZ(), player.getVisionRadius());
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                if (player.canSee(wx, wy, player.getZ())) {
                    terminal.write(world.getGlyph(wx, wy, player.getZ()), x, y, world.getColor(wx, wy, player.getZ()));
                } else {
                    terminal.write(fov.getTile(wx, wy, player.getZ()).getGlyph(), x, y, Color.darkGray);
                }

            }
        }
    }

    private void displayCreatures(AsciiPanel terminal, int left, int top) {
        if (world.getCreatures() != null) {
            for (Creature c : world.getCreatures()) {
                if (c.getX() >= left && c.getX() < left + screenWidth && c.getY() >= top && c.getY() < top + screenHeight && c.getZ() == player.getZ()) {
                    terminal.write(c.getGlyph(), c.getX() - left, c.getY() - top, c.getColor());
                }
            }
        }
    }

    private void createWorld() {
        world = new WorldBuilder(31, 90, 5).makeCaves().build();
    }

    private void createCreatures(CreatureFactory creatureFactory) {
        fov = new FieldOfView(world);
        player = creatureFactory.newPlayer(messages, fov);
        for (int z = 0; z < world.getDepth(); z++) {
            for (int i = 0; i < 8; i++) {
                creatureFactory.newFungus(z);
            }
        }
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }
}
