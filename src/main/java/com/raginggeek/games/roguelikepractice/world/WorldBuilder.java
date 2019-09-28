package com.raginggeek.games.roguelikepractice.world;

public class WorldBuilder {
    private int height;
    private int width;
    private Tile[][] tiles;

    public WorldBuilder(int height, int width) {
        this.height = height;
        this.width = width;
        this.tiles = new Tile[width][height];
    }

    private WorldBuilder randomizeTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
            }
        }
        return this;
    }

    private WorldBuilder smooth(int times) {
        Tile[][] smoothedTiles = new Tile[width][height];
        for (int cycle = 0; cycle < times; cycle++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int floors = 0;
                    int rocks = 0;

                    for (int ox = -1; ox < 2; ox++) {
                        for (int oy = -1; oy < 2; oy++) {
                            if (x + ox < 0 || x + ox >= width || y + oy < 0 || y + oy >= height) {
                                continue;
                            }

                            if (tiles[x + ox][y + oy] == Tile.FLOOR) {
                                floors++;
                            } else {
                                rocks++;
                            }
                        }
                    }
                    smoothedTiles[x][y] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
                }
            }
            tiles = smoothedTiles;
        }
        return this;
    }

    public WorldBuilder makeCaves() {
        return randomizeTiles().smooth(8);
    }

    public World build() {
        return new World(tiles);
    }
}
