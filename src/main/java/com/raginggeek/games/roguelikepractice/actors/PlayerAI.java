package com.raginggeek.games.roguelikepractice.actors;

import com.raginggeek.games.roguelikepractice.world.Tile;

import java.util.List;

public class PlayerAI extends CreatureAI {
    protected static String NAME = "You";
    private List<String> messages;

    public PlayerAI(Creature creature, List<String> messages) {
        super(creature);
        this.messages = messages;
    }

    public void onEnter(int x, int y, int z, Tile tile) {
        if (tile.isGround()) {
            creature.setX(x);
            creature.setY(y);
            creature.setZ(z);
        } else if (tile.isDiggable()) {
            creature.dig(x, y, z);
        }
    }

    public void onNotify(String message) {
        messages.add(message);
    }

    public void onUpdate() {

    }

    public String getName() {
        return NAME;
    }
}
