package com.raginggeek.games.roguelikepractice.world;

import com.raginggeek.games.roguelikepractice.entities.Entity;
import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    private Tile[][][] tiles;
    private Item[][][] items;
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
        this.items = new Item[width][height][depth];
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
        if (creature != null) {
            return creature.getGlyph();
        }

        if (getItem(x, y, z) != null) {
            return getItem(x, y, z).getGlyph();
        }

        return getTile(x, y, z).getGlyph();
    }

    public Color getColor(int x, int y, int z) {
        Creature creature = getCreature(x, y, z);
        if (creature != null) {
            return creature.getColor();
        }

        if (getItem(x, y, z) != null) {
            return getItem(x, y, z).getColor();
        }

        return getTile(x, y, z).getColor();
    }

    public void dig(int x, int y, int z) {
        if (tiles[x][y][z].isDiggable()) {
            tiles[x][y][z] = Tile.FLOOR;
        }
    }

    public void addAtEmptyLocation(Entity entity, int z) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        } while (!canEnter(x, y, z));

        if (entity instanceof Creature) {
            Creature creature = (Creature) entity;
            creature.setX(x);
            creature.setY(y);
            creature.setZ(z);
            creatures.add(creature);
        }
        if (entity instanceof Item) {
            Item item = (Item) entity;
            items[x][y][z] = item;
        }

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

    public Item getItem(int x, int y, int z) {
        return items[x][y][z];
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

    public void remove(int x, int y, int z) {
        items[x][y][z] = null;
    }

    public boolean addAtEmptySpace(Item item, int x, int y, int z) {
        if (item == null) {
            return true;
        }
        List<Point> points = new ArrayList<>();
        List<Point> checked = new ArrayList<>();

        points.add(new Point(x, y, z));

        while (!points.isEmpty()) {
            Point p = points.remove(0);
            checked.add(p);
            if (!getTile(p.getX(), p.getY(), p.getZ()).isGround()) {
                continue;
            }

            if (items[p.getX()][p.getY()][p.getZ()] == null) {
                items[p.getX()][p.getY()][p.getZ()] = item;
                Creature c = this.getCreature(p.getX(), p.getY(), p.getZ());
                if (c != null) {
                    c.doEvent("A %s lands between your feet.", item.getName());
                }
                return true;
            } else {
                List<Point> neighbors = p.neighbors8();
                neighbors.removeAll(checked);
                points.addAll(neighbors);
            }
        }
        return false;
    }
}
