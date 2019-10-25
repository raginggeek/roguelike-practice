package com.raginggeek.games.roguelikepractice.screens;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.world.Line;
import com.raginggeek.games.roguelikepractice.world.Point;

import java.awt.event.KeyEvent;

public abstract class TargettingScreen implements Screen {
    protected Creature player;
    protected String caption;
    private int sx;
    private int sy;
    private int x;
    private int y;

    public TargettingScreen(Creature player, String caption, int sx, int sy) {
        this.player = player;
        this.caption = caption;
        this.sx = sx;
        this.sy = sy;
    }

    public void displayOutput(AsciiPanel terminal) {
        for (Point p : new Line(sx, sy, sx + x, sy + y)) {
            if (p.getX() < 0 || p.getX() >= 80 || p.getY() < 0 || p.getY() >= 24) {
                continue;
            }

            terminal.write('x', p.getX(), p.getY(), AsciiPanel.brightMagenta);
        }
        terminal.clear(' ', 0, 23, 80, 1);
        terminal.write(caption, 0, 23);
    }

    public Screen respondToUserInput(KeyEvent key) {
        int px = x;
        int py = y;

        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                x--;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                x++;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_J:
                y--;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_K:
                y++;
                break;
            case KeyEvent.VK_Y:
                x--;
                y--;
                break;
            case KeyEvent.VK_U:
                x++;
                y--;
                break;
            case KeyEvent.VK_B:
                x--;
                y++;
                break;
            case KeyEvent.VK_N:
                x++;
                y++;
                break;
            case KeyEvent.VK_ENTER:
                selectWorldCoordinate(player.getX() + x, player.getY() + y, sx + x, sy + y);
                return null;
            case KeyEvent.VK_ESCAPE:
                return null;
        }

        if (!isAcceptable(player.getX() + x, player.getY() + y)) {
            x = px;
            y = py;
        }

        enterWorldCoordinate(player.getX() + x, player.getY() + y, sx + x, sy + y);

        return this;
    }

    public boolean isAcceptable(int x, int y) {
        return true;
    }

    public abstract void enterWorldCoordinate(int x, int y, int screenX, int screenY);

    public abstract void selectWorldCoordinate(int x, int y, int screenX, int screenY);
}
