package com.raginggeek.games.roguelikepractice.entities.items;

import com.raginggeek.games.roguelikepractice.entities.Entity;

import java.awt.*;

public class Item implements Entity {
    private char glyph;
    private Color color;
    private String name;
    private int foodValue;
    private int attackValue;
    private int defenseValue;

    public Item(char glyph, Color color, String name) {
        this.glyph = glyph;
        this.color = color;
        this.name = name;
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getFoodValue() {
        return foodValue;
    }

    public void modifyFoodValue(int amount) {
        foodValue += amount;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void modifyAttackValue(int amount) {
        attackValue += amount;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void modifyDefenseValue(int amount) {
        defenseValue += amount;
    }
}
