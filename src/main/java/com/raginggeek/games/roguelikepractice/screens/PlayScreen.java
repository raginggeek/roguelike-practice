package com.raginggeek.games.roguelikepractice.screens;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.actors.CreatureFactory;
import com.raginggeek.games.roguelikepractice.entities.actors.capabilities.FieldOfView;
import com.raginggeek.games.roguelikepractice.entities.items.Item;
import com.raginggeek.games.roguelikepractice.entities.items.ItemFactory;
import com.raginggeek.games.roguelikepractice.world.Tile;
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
    private Screen subscreen;

    public PlayScreen() {
        screenWidth = 80;
        screenHeight = 21;
        messages = new ArrayList<>();
        createWorld();
        CreatureFactory creatureFactory = new CreatureFactory(world);
        ItemFactory itemFactory = new ItemFactory(world);
        createCreatures(creatureFactory);
        createItems(itemFactory);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();
        displayTiles(terminal, left, top);
        displayCreatures(terminal, left, top);
        String stats = String.format(" %3d/%3d hp %8s", player.getHp(), player.getMaxHp(), hunger());
        displayMessages(terminal, messages);
        terminal.write(stats, 1, 23);
        if (subscreen != null) {
            subscreen.displayOutput(terminal);
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int level = player.getLevel();
        if (subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
        } else {
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
                case KeyEvent.VK_D:
                    subscreen = new DropScreen(player);
                    break;
                case KeyEvent.VK_E:
                    subscreen = new EatScreen(player);
                    break;
                case KeyEvent.VK_W:
                    subscreen = new EquipScreen(player);
                    break;
                case KeyEvent.VK_X:
                    subscreen = new ExamineScreen(player);
                    break;
                case KeyEvent.VK_SEMICOLON:
                    subscreen = new LookScreen(player, "looking", player.getX() - getScrollX(), player.getY() - getScrollY());
                    break;
                case KeyEvent.VK_T:
                    subscreen = new ThrowScreen(player, player.getX() - getScrollX(), player.getY() - getScrollY());
                    break;
                case KeyEvent.VK_F:
                    if (player.getWeapon() == null || player.getWeapon().getRangedAttackValue() == 0) {
                        player.notify("You don't have a ranged weapon equipped.");
                    } else {
                        subscreen = new FireWeaponScreen(player, player.getX() - getScrollX(), player.getY() - getScrollY());
                    }
                    break;
            }
            switch (key.getKeyChar()) {
                case '<':
                    if (userIsTryingToExit()) {
                        return userExits();
                    } else {
                        player.moveBy(0, 0, -1);
                    }
                    break;
                case '>':
                    player.moveBy(0, 0, 1);
                    break;
                case 'g':
                case ',':
                    player.pickup();
                    break;
                case '?':
                    subscreen = new HelpScreen();
                    break;
            }
        }

        if (subscreen == null) {
            world.update();
        }
        if (player.getHp() < 1) {
            return new LoseScreen();
        }
        if (player.getLevel() > level) {
            subscreen = new LevelUpScreen(player, player.getLevel() - level);
        }
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
            for (int i = 0; i < 20; i++) {
                creatureFactory.newBat(z);
            }
            for (int i = 0; i < z + 3; i++) {
                creatureFactory.newZombie(z, player);
            }
        }
    }

    private void createItems(ItemFactory itemFactory) {
        for (int z = 0; z < world.getDepth(); z++) {
            for (int i = 0; i < world.getWidth() * world.getHeight() / 20; i++) {
                itemFactory.newRock(z);
            }
            for (int i = 0; i < world.getWidth() * world.getHeight() / 640; i++) {
                itemFactory.randomWeapon(z);
            }
            for (int i = 0; i < world.getWidth() * world.getHeight() / 640; i++) {
                itemFactory.randomArmor(z);
            }
        }

        itemFactory.newMcGuffin(world.getDepth() - 1);
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }

    private boolean userIsTryingToExit() {
        return player.getZ() == 0 &&
                world.getTile(player.getX(), player.getY(), player.getZ()) == Tile.STAIRS_UP;
    }

    private Screen userExits() {
        for (Item item : player.getInventory().getItems()) {
            if (item != null && item.getName().equals("Holy Relic")) {
                return new WinScreen();
            }
        }
        return new LoseScreen();
    }

    private String hunger() {
        if (player.getFood() < player.getMaxFood() * 0.1) {
            return "Starving";
        } else if (player.getFood() < player.getMaxFood() * 0.2) {
            return "Hungry";
        } else if (player.getFood() > player.getMaxFood() * 0.9) {
            return "Stuffed";
        } else if (player.getFood() > player.getMaxFood() * 0.8) {
            return "Full";
        } else {
            return "";
        }
    }
}
