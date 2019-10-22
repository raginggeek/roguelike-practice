package com.raginggeek.games.roguelikepractice.world;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;

import java.util.List;

public class Path {
    private static PathFinder pf = new PathFinder();

    private List<Point> points;

    public Path(Creature creature, int x, int y) {
        points = pf.findPath(creature,
                new Point(creature.getX(), creature.getY(), creature.getZ()),
                new Point(x, y, creature.getZ()),
                300);
    }

    public List<Point> getPoints() {
        return points;
    }
}
