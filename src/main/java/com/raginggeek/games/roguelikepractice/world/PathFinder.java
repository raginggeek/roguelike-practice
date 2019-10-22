package com.raginggeek.games.roguelikepractice.world;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PathFinder {
    private ArrayList<Point> open;
    private ArrayList<Point> closed;
    private HashMap<Point, Point> parents;
    private HashMap<Point, Integer> totalCost;

    public PathFinder() {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.parents = new HashMap<>();
        this.totalCost = new HashMap<>();
    }

    public ArrayList<Point> findPath(Creature creature, Point start,
                                     Point end, int maxTries) {
        open.clear();
        closed.clear();
        parents.clear();
        totalCost.clear();
        open.add(start);
        for (int tries = 0; tries < maxTries && open.size() > 0; tries++) {
            Point closest = getClosestPoint(end);

            open.remove(closest);
            closed.add(closest);
            if (closest.equals(end)) {
                return createPath(start, closest);
            } else {
                checkNeighbors(creature, end, closest);
            }
        }
        return null;
    }

    private int calculateHeuristicCost(Point from, Point to) {
        return Math.max(Math.abs(from.getX() - to.getX()),
                Math.abs(from.getY() - to.getY()));
    }

    private int calculateCostToGetTo(Point from) {
        return parents.get(from) == null ? 0 :
                (1 + calculateCostToGetTo(parents.get(from)));
    }

    private int calculateTotalCost(Point from, Point to) {
        return totalCost.computeIfAbsent(from, key ->
                calculateCostToGetTo(from) + calculateHeuristicCost(from, to));
    }

    private void reParent(Point child, Point parent) {
        parents.put(child, parent);
        totalCost.remove(child);
    }

    private Point getClosestPoint(Point end) {
        Point closest = open.get(0);
        for (Point other : open) {
            if (calculateTotalCost(other, end) < calculateTotalCost(closest, end)) {
                closest = other;
            }
        }
        return closest;
    }

    private void checkNeighbors(Creature creature, Point end, Point closest) {
        for (Point neighbor : closest.neighbors8()) {
            if (closed.contains(neighbor) ||
                    !creature.canEnter(neighbor.getX(), neighbor.getY(), neighbor.getZ()) &&
                            !neighbor.equals(end)) {
                continue;
            }

            if (open.contains(neighbor)) {
                reParentNeighborIfNecessary(closest, neighbor);
            } else {
                reParentNeighbor(closest, neighbor);
            }
        }
    }

    private void reParentNeighbor(Point closest, Point neighbor) {
        reParent(neighbor, closest);
        open.add(neighbor);
    }

    private void reParentNeighborIfNecessary(Point closest, Point neighbor) {
        Point originalParent = parents.get(neighbor);
        double currentCost = calculateCostToGetTo(neighbor);
        reParent(neighbor, closest);
        double reparentCost = calculateCostToGetTo(neighbor);

        if (reparentCost < currentCost) {
            open.remove(neighbor);
        } else {
            reParent(neighbor, originalParent);
        }
    }

    private ArrayList<Point> createPath(Point start, Point end) {
        ArrayList<Point> path = new ArrayList<>();

        while (!end.equals(start)) {
            path.add(end);
            end = parents.get(end);
        }

        Collections.reverse(path);
        return path;
    }

}
