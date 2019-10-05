package com.raginggeek.games.roguelikepractice.actors;

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
