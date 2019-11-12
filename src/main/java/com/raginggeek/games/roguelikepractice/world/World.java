package com.raginggeek.games.roguelikepractice.world;

import com.raginggeek.games.roguelikepractice.entities.Entity;
import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public Tile getTile(Point location) {
        if (location.getX() < 0 || location.getX() >= width ||
                location.getY() < 0 || location.getY() >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[location.getX()][location.getY()][location.getZ()];
        }
    }

    public char getGlyph(Point location) {
        Creature creature = getCreature(location);
        if (creature != null) {
            return creature.getGlyph();
        }

        if (getItem(location) != null) {
            return getItem(location).getGlyph();
        }

        return getTile(location).getGlyph();
    }

    public Color getColor(Point location) {
        Creature creature = getCreature(location);
        if (creature != null) {
            return creature.getColor();
        }

        if (getItem(location) != null) {
            return getItem(location).getColor();
        }

        return getTile(location).getColor();
    }

    public void dig(Point location) {
        if (tiles[location.getX()][location.getY()][location.getZ()].isDiggable()) {
            tiles[location.getX()][location.getY()][location.getZ()] = Tile.FLOOR;
        }
    }

    public void addAtEmptyLocation(Entity entity, int z) {
        int x;
        int y;
        Point point;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        } while (!canEnter(point = new Point(x, y, z)));

        if (entity instanceof Creature) {
            Creature creature = (Creature) entity;
            creature.setLocation(point);
            creatures.add(creature);
        }
        if (entity instanceof Item) {
            Item item = (Item) entity;
            items[x][y][z] = item;
        }

    }

    public Creature getCreature(Point location) {
        if (creatures != null) {
            for (Creature c : creatures) {
                if (location.equals(c.getLocation())) {
                    return c;
                }
            }
        }
        return null;
    }

    public Item getItem(Point location) {
        //log.info("attempting to gather item at " + location.getX() + "," + location.getY() + "," + location.getZ());
        return items[location.getX()][location.getY()][location.getZ()];
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }

    public boolean canEnter(Point location) {
        return location.getX() > 0 && location.getX() < width &&
                location.getY() > 0 && location.getY() < height &&
                location.getZ() >= 0 && location.getZ() < depth &&
                tiles[location.getX()][location.getY()][location.getZ()].isGround() &&
                getCreature(location) == null;
    }

    public void update() {
        List<Creature> toUpdate = new ArrayList<>(creatures);
        for (Creature creature : toUpdate) {
            creature.update();
        }
    }

    public void remove(Point location) {
        items[location.getX()][location.getY()][location.getZ()] = null;
    }

    public boolean addAtEmptySpace(Item item, Point location) {
        if (item == null) {
            return true;
        }
        List<Point> points = new ArrayList<>();
        List<Point> checked = new ArrayList<>();

        points.add(location);

        while (!points.isEmpty()) {
            Point p = points.remove(0);
            checked.add(p);
            if (!getTile(p).isGround()) {
                continue;
            }

            if (items[p.getX()][p.getY()][p.getZ()] == null) {
                items[p.getX()][p.getY()][p.getZ()] = item;
                Creature c = this.getCreature(p);
                if (c != null) {
                    c.doEvent("A %s lands between your feet.", item.getAppearance());
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

    public void removeItem(Item item) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    if (items[x][y][z] == item) {
                        items[x][y][z] = null;
                        return;
                    }
                }
            }
        }
    }

    public void add(Creature pet) {
        creatures.add(pet);
    }
}
