package com.raginggeek.games.roguelikepractice.world;

import asciiPanel.AsciiPanel;

import java.awt.*;

public enum Tile {
    FLOOR((char) 250, AsciiPanel.yellow, "A dirt and rock cave floor."),
    WALL((char) 177, AsciiPanel.yellow, "A dirt and rock cave wall."),
    BOUNDS('x', AsciiPanel.brightBlack, "Beyond the edge of the world."),
    STAIRS_DOWN('>', AsciiPanel.white, "A stone staircase that goes down."),
    STAIRS_UP('<', AsciiPanel.white, "A stone staircase that goes up."),
    UNKNOWN(' ', AsciiPanel.white, "(unknown)");

    private char glyph;
    private Color color;
    private String details;

    Tile(char glyph, Color color, String details) {
        this.glyph = glyph;
        this.color = color;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public boolean isDiggable() {
        return this == Tile.WALL;
    }

    public boolean isGround() {
        return this != WALL && this != BOUNDS;
    }

}
