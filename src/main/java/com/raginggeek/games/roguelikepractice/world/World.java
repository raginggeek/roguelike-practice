package com.raginggeek.games.roguelikepractice.world;

import com.raginggeek.games.roguelikepractice.actors.Creature;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    private Tile[][] tiles;
    private int width;
    private int height;
    private List<Creature> creatures;

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.creatures = new ArrayList<>();
        this.width = tiles.length;
        this.height = tiles[0].length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        }
    }

    public char getGlyph(int x, int y) {
        return getTile(x, y).getGlyph();
    }

    public Color getColor(int x, int y) {
        return getTile(x, y).getColor();
    }

    public void dig(int x, int y) {
        if (tiles[x][y].isDiggable()) {
            tiles[x][y] = Tile.FLOOR;
        }
    }

    public void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        } while (!canEnter(x, y));
        creature.setX(x);
        creature.setY(y);
        creatures.add(creature);
    }

    public Creature getCreature(int x, int y) {
        if (creatures != null) {
            for (Creature c : creatures) {
                if (c.getX() == x && c.getY() == y) {
                    return c;
                }
            }
        }
        return null;
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }

    public boolean canEnter(int x, int y) {
        return x > 0 && x < width && y > 0 && y < height && tiles[x][y].isGround() && getCreature(x, y) == null;
    }

    public void update() {
        List<Creature> toUpdate = new ArrayList<>(creatures);
        for (Creature creature : toUpdate) {
            creature.update();
        }
    }
}
