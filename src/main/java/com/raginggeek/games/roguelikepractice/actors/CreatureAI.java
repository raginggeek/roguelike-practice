package com.raginggeek.games.roguelikepractice.actors;

import com.raginggeek.games.roguelikepractice.world.Line;
import com.raginggeek.games.roguelikepractice.world.Point;
import com.raginggeek.games.roguelikepractice.world.Tile;

public abstract class CreatureAI {
    protected static String NAME = "creature";
    protected Creature creature;

    public CreatureAI(Creature creature) {
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    public String getName() {
        return NAME;
    }

    public boolean canSee(int wx, int wy, int wz) {
        if (creature.getZ() != wz) {
            return false; // can't see through floors and ceilings
        }

        if ((creature.getX() - wx) * (creature.getX() - wx)
                + (creature.getY() - wy) * (creature.getY() - wy)
                > creature.getVisionRadius() * creature.getVisionRadius()) {
            return false;
        }

        for (Point p : new Line(creature.getX(), creature.getY(), wx, wy)) {
            if (creature.getTile(p.getX(), p.getY(), wz).isGround() || p.getX() == wx && p.getY() == wy) {
                continue;
            }
            return false;
        }
        return true;
    }

    public abstract void onEnter(int x, int y, int z, Tile tile);

    public abstract void onUpdate();

    public abstract void onNotify(String message);
}
