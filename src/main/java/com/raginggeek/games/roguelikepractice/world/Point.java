package com.raginggeek.games.roguelikepractice.world;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class Point {
    private int x;
    private int y;
    private int z;

    public List<Point> neighbors8() {
        List<Point> points = new ArrayList<>();
        for (int ox = -1; ox < 2; ox++) {
            for (int oy = -1; oy < 2; oy++) {
                if (ox == 0 && oy == 0) {
                    continue;
                } else {
                    int nx = x + ox;
                    int ny = y + oy;
                    if (nx < 0 || ny < 0) {
                        continue;
                    } else {
                        points.add(new Point(x + ox, y + oy, z));
                    }

                }
            }
        }
        Collections.shuffle(points);
        return points;
    }

    public void add(Point vector) {
        x += vector.getX();
        y += vector.getY();
        z += vector.getZ();
    }

    public Point clone() {
        return new Point(x, y, z);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point other = (Point) obj;
        if (x != other.getX()) {
            return false;
        }
        if (y != other.getY()) {
            return false;
        }
        return z == other.getZ();
    }
}
