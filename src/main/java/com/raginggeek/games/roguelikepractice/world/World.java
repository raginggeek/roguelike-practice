package com.raginggeek.games.roguelikepractice.world;

import com.raginggeek.games.roguelikepractice.actors.Creature;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    private Tile[][][] tiles;
    private int width;
    private int height;
    private int depth;
    private List<Creature> creatures;

    public World(Tile[][][] tiles) {
        this.tiles = tiles;
        this.creatures = new ArrayList<>();
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.depth = tiles[0][0].length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public Tile getTile(int x, int y, int z) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[x][y][z];
        }
    }

    public char getGlyph(int x, int y, int z) {
        Creature creature = getCreature(x, y, z);
        return creature != null ? creature.getGlyph() : getTile(x, y, z).getGlyph();
    }

    public Color getColor(int x, int y, int z) {
        Creature creature = getCreature(x, y, z);
        return creature != null ? creature.getColor() : getTile(x, y, z).getColor();
    }

    public void dig(int x, int y, int z) {
        if (tiles[x][y][z].isDiggable()) {
            tiles[x][y][z] = Tile.FLOOR;
        }
    }

    public void addAtEmptyLocation(Creature creature, int z) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        } while (!canEnter(x, y, z));
        creature.setX(x);
        creature.setY(y);
        creature.setZ(z);
        creatures.add(creature);
    }

    public Creature getCreature(int x, int y, int z) {
        if (creatures != null) {
            for (Creature c : creatures) {
                if (c.getX() == x && c.getY() == y && c.getZ() == z) {
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

    public boolean canEnter(int x, int y, int z) {
        return x > 0 && x < width && y > 0 && y < height && z >= 0 && z < depth && tiles[x][y][z].isGround() && getCreature(x, y, z) == null;
    }

    public void update() {
        List<Creature> toUpdate = new ArrayList<>(creatures);
        for (Creature creature : toUpdate) {
            creature.update();
        }
    }
}
