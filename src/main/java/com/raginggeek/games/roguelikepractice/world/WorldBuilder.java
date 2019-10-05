package com.raginggeek.games.roguelikepractice.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldBuilder {
    private int height;
    private int width;
    private int depth;
    private int nextRegion;
    private Tile[][][] tiles;
    private int[][][] regions;

    public WorldBuilder(int height, int width, int depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.tiles = new Tile[width][height][depth];
        this.nextRegion = 1;
    }

    public WorldBuilder makeCaves() {
        return randomizeTiles().smooth(8).createRegions().connectRegions().addExitStairs();
    }

    public WorldBuilder connectRegions() {
        for (int z = 0; z < depth - 1; z++) {
            connectRegionsDown(z);
        }
        return this;
    }

    public List<Point> findRegionOverlaps(int z, int r1, int r2) {
        ArrayList<Point> candidates = new ArrayList<Point>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y][z] == Tile.FLOOR &&
                        tiles[x][y][z + 1] == Tile.FLOOR &&
                        regions[x][y][z] == r1 &&
                        regions[x][y][z + 1] == r2) {
                    candidates.add(new Point(x, y, z));
                }
            }
        }
        Collections.shuffle(candidates);
        return candidates;
    }

    public World build() {
        return new World(tiles);
    }

    private WorldBuilder smooth(int times) {
        Tile[][][] smoothedTiles = new Tile[width][height][depth];
        for (int cycle = 0; cycle < times; cycle++) {
            for (int z = 0; z < depth; z++) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int floors = 0;
                        int rocks = 0;
                        for (int ox = -1; ox < 2; ox++) {
                            for (int oy = -1; oy < 2; oy++) {
                                if (x + ox < 0 || x + ox >= width || y + oy < 0 || y + oy >= height) {
                                    continue;
                                }

                                if (tiles[x + ox][y + oy][z] == Tile.FLOOR) {
                                    floors++;
                                } else {
                                    rocks++;
                                }
                            }
                        }
                        smoothedTiles[x][y][z] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
                    }
                }
            }
            tiles = smoothedTiles;
        }
        return this;
    }

    private WorldBuilder randomizeTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    tiles[x][y][z] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
                }
            }
        }
        return this;
    }

    private WorldBuilder createRegions() {
        regions = new int[width][height][depth];

        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (tiles[x][y][z] != Tile.WALL && regions[x][y][z] == 0) {
                        int size = fillRegion(nextRegion++, x, y, z);
                        if (size < 25)
                            removeRegion(nextRegion - 1, z);
                    }
                }
            }
        }
        return this;
    }

    private void removeRegion(int region, int z) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (regions[x][y][z] == region) {
                    regions[x][y][z] = 0;
                    tiles[x][y][z] = Tile.WALL;
                }
            }
        }
    }

    private int fillRegion(int region, int x, int y, int z) {
        int size = 1;
        ArrayList<Point> open = new ArrayList<>();
        open.add(new Point(x, y, z));
        regions[x][y][z] = region;
        while (!open.isEmpty()) {
            Point p = open.remove(0);
            for (Point neighbor : p.neighbors8()) {
                if (!open.contains(neighbor)) {
                    if (neighbor.getX() < 0 || neighbor.getY() < 0 || neighbor.getX() >= width || neighbor.getY() >= height) {
                        continue;
                    }
                    if (regions[neighbor.getX()][neighbor.getY()][neighbor.getZ()] > 0
                            || tiles[neighbor.getX()][neighbor.getY()][neighbor.getZ()] == Tile.WALL) {
                        continue;
                    } else {
                        size++;
                        regions[neighbor.getX()][neighbor.getY()][neighbor.getZ()] = region;
                        open.add(neighbor);
                    }
                }
            }
        }
        return size;
    }

    private void connectRegionsDown(int z) {
        List<String> connected = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String region = regions[x][y][z] + "," + regions[x][y][z + 1];
                if (tiles[x][y][z] == Tile.FLOOR &&
                        tiles[x][y][z + 1] == Tile.FLOOR &&
                        !connected.contains(region)) {
                    connected.add(region);
                    connectRegionsDown(z, regions[x][y][z], regions[x][y][z + 1]);
                }
            }
        }
    }

    private void connectRegionsDown(int z, int r1, int r2) {
        List<Point> candidates = findRegionOverlaps(z, r1, r2);

        int stairs = 0;
        do {
            Point p = candidates.remove(0);
            tiles[p.getX()][p.getY()][p.getZ()] = Tile.STAIRS_DOWN;
            tiles[p.getX()][p.getY()][p.getZ() + 1] = Tile.STAIRS_UP;
            stairs++;
        } while (candidates.size() / stairs > 250);
    }

    private WorldBuilder addExitStairs() {
        int x = -1;
        int y = -1;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        } while (tiles[x][y][0] != Tile.FLOOR);

        tiles[x][y][0] = Tile.STAIRS_UP;
        return this;
    }

}
