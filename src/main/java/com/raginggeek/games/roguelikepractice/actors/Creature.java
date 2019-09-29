package com.raginggeek.games.roguelikepractice.actors;

import com.raginggeek.games.roguelikepractice.world.World;

import java.awt.*;

public class Creature {
    private World world;

    private int x;
    private int y;
    private char glyph;
    private Color color;
    private CreatureAI ai;

    public Creature(World world, char glyph, Color color) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
    }

    public void setCreatureAi(CreatureAI ai) {
        this.ai = ai;
    }

    public Color getColor() {
        return color;
    }

    public char getGlyph() {
        return glyph;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void dig(int wx, int wy) {
        this.world.dig(wx, wy);
    }

    public void moveBy(int mx, int my) {
        Creature opponent = world.getCreature(x + mx, y + my);

        if (opponent == null) {
            ai.onEnter(x + mx, y + my, world.getTile(x + mx, y + my));
        } else {
            attack(opponent);
        }
    }

    public void attack(Creature opponent) {
        world.removeCreature(opponent);
    }

    public boolean canEnter(int x, int y) {
        return world.canEnter(x, y);
    }

    public void update() {
        ai.onUpdate();
    }


}
