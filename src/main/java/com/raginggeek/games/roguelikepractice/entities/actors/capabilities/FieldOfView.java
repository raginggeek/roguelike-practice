package com.raginggeek.games.roguelikepractice.entities.actors.capabilities;

import com.raginggeek.games.roguelikepractice.world.Line;
import com.raginggeek.games.roguelikepractice.world.Point;
import com.raginggeek.games.roguelikepractice.world.Tile;
import com.raginggeek.games.roguelikepractice.world.World;

public class FieldOfView {
    private World world;
    private int depth;

    private boolean[][] visible;
    private Tile[][][] tiles;

    public FieldOfView(World world) {
        this.world = world;
        this.visible = new boolean[world.getWidth()][world.getHeight()];
        this.tiles = new Tile[world.getWidth()][world.getHeight()][world.getDepth()];

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                for (int z = 0; z < world.getDepth(); z++) {
                    tiles[x][y][z] = Tile.UNKNOWN;
                }
            }
        }
    }

    public void update(int wx, int wy, int wz, int r) {
        depth = wz;
        visible = new boolean[world.getWidth()][world.getHeight()];

        for (int x = -r; x < r; x++) {
            for (int y = -r; y < r; y++) {
                if (x * x + y * y > r * r) {
                    continue;
                }

                if (wx + x < 0 || wx + x >= world.getWidth() ||
                        wy + y < 0 || wy + y >= world.getHeight()) {
                    continue;
                }

                for (Point p : new Line(wx, wy, wx + x, wy + y)) {
                    Tile tile = world.getTile(p.getX(), p.getY(), wz);
                    visible[p.getX()][p.getY()] = true;
                    tiles[p.getX()][p.getY()][wz] = tile;

                    if (!tile.isGround()) {
                        break;
                    }
                }
            }
        }
    }

    public boolean isVisible(int x, int y, int z) {
        return z == depth &&
                x >= 0 &&
                x < visible.length &&
                y < visible[0].length &&
                visible[x][y];
    }

    public Tile getTile(int x, int y, int z) {
        return tiles[x][y][z];
    }
}
