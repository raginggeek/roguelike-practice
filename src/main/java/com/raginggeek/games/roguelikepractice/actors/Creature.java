package com.raginggeek.games.roguelikepractice.actors;

import com.raginggeek.games.roguelikepractice.world.World;

import java.awt.*;

public class Creature {
    private World world;

    private int x;
    private int y;
    private char glyph;
    private String name;
    private Color color;
    private CreatureAI ai;
    private int maxHp;
    private int hp;
    private int attackValue;
    private int defenseValue;

    public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackValue = attack;
        this.defenseValue = defense;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public String getName() {
        return name;
    }

    public void setCreatureAi(CreatureAI ai) {
        this.ai = ai;
        this.name = ai.getName();
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
        int damage = Math.max(0, attackValue - opponent.getDefenseValue());
        damage = (int) (Math.random() * damage) + 1;

        doEvent("attack the %s for %d damage", opponent.getName(), damage);
        opponent.notify("The %s attacks you for %d damage.", opponent.getName(), damage);
        opponent.modifyHp(-damage);
    }

    public void modifyHp(int amount) {
        hp += amount;

        if (hp < 1) {
            doEvent("die");
            world.removeCreature(this);
        }
    }

    public boolean canEnter(int x, int y) {
        return world.canEnter(x, y);
    }

    public void update() {
        ai.onUpdate();
    }

    public void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    public void doEvent(String message, Object... params) {
        int r = 9;
        for (int ox = -r; ox < r + 1; ox++) {
            for (int oy = -r; oy < r + 1; oy++) {
                if (ox * ox + oy * oy <= r * r) {
                    Creature other = world.getCreature(x + ox, y + oy);
                    if (other != null) {
                        if (other == this)
                            other.notify("You " + message + ".", params);
                        else {
                            other.notify(String.format(
                                    "The '%s' %s.",
                                    name, makeSecondPerson(message)),
                                    params);
                        }
                    }
                }
            }
        }
    }

    private String makeSecondPerson(String text) {
        String[] words = text.split(" ");
        words[0] = words[0] + "s";

        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(" ");
            builder.append(word);
        }
        return builder.toString().trim();
    }
}
