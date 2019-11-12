package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.actors.capabilities.FieldOfView;
import com.raginggeek.games.roguelikepractice.world.Point;
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

    public void onEnter(Point location, Tile tile) {
        if (tile.isGround()) {
            creature.setLocation(location);
        } else if (tile.isDiggable()) {
            creature.dig(location);
        }
    }

    public void onNotify(String message) {
        messages.add(message);
    }

    public void onGainLevel() {

    }

    public void onUpdate() {
    }

    public boolean canSee(Point location) {
        return fov.isVisible(location);
    }

    public Tile getRememberedTile(Point rememberedTile) {
        return fov.getTile(rememberedTile);
    }
}
