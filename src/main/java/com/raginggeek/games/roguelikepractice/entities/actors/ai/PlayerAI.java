package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.actors.capabilities.FieldOfView;
import com.raginggeek.games.roguelikepractice.world.Tile;

import java.util.List;

public class PlayerAI extends CreatureAI {
    private List<String> messages;
    private FieldOfView fov;

    public PlayerAI(Creature creature, List<String> messages, FieldOfView fov) {
        super(creature, "You");
        this.messages = messages;
        this.fov = fov;
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

    public void onGainLevel() {

    }

    public void onUpdate() {

    }

    public boolean canSee(int wx, int wy, int wz) {
        return fov.isVisible(wx, wy, wz);
    }

    public Tile getRememberedTile(int wx, int wy, int wz) {
        return fov.getTile(wx, wy, wz);
    }
}
