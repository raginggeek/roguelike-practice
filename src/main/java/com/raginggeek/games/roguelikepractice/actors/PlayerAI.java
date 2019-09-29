package com.raginggeek.games.roguelikepractice.actors;

import com.raginggeek.games.roguelikepractice.world.Tile;

public class PlayerAI extends CreatureAI {
    public PlayerAI(Creature creature) {
        super(creature);
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.setX(x);
            creature.setY(y);
        } else if (tile.isDiggable()) {
            creature.dig(x, y);
        }
    }

    public void onUpdate() {

    }
}
