package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.world.Path;
import com.raginggeek.games.roguelikepractice.world.Point;

import java.util.List;

public class ZombieAI extends CreatureAI {
    private Creature player;

    public ZombieAI(Creature creature, Creature player) {
        super(creature, "zombie");
        this.player = player;
    }

    public void onUpdate() {
        if (Math.random() >= 0.2) {
            if (creature.canSee(player.getX(), player.getY(), player.getZ())) {
                hunt(player);
            } else {
                wander();
            }
        }
    }

    @Override
    public void onNotify(String message) {
    }

    public void hunt(Creature target) {
        List<Point> points = new Path(creature, target.getX(), target.getY()).getPoints();

        int mx = points.get(0).getX() - creature.getX();
        int my = points.get(0).getY() - creature.getY();

        creature.moveBy(mx, my, 0);
    }


}
