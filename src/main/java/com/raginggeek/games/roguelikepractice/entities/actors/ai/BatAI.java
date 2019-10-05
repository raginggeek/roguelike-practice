package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;

public class BatAI extends CreatureAI {

    public BatAI(Creature creature) {
        super(creature, "bat");
    }

    public void onUpdate() {
        wander();
        wander();
    }

    @Override
    public void onNotify(String message) {

    }
}
