package com.raginggeek.games.roguelikepractice.entities.items;

import com.raginggeek.games.roguelikepractice.entities.Entity;

import java.awt.*;

public class Item implements Entity {
    private char glyph;
    private Color color;
    private String name;

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
}
