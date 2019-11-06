package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;

public class ZombieAI extends AggressiveCreatureAI {
    private Creature player;

    public ZombieAI(Creature creature, Creature player) {
        super(creature, "zombie");
        this.player = player;
    }

    public void onUpdate() {
        if (Math.random() >= 0.2) {
            if (creature.canSee(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ())) {
                hunt(player);
            } else {
                wander();
            }
        }
    }

    @Override
    public void onNotify(String message) {
    }
}
