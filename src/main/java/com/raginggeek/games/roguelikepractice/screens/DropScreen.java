package com.raginggeek.games.roguelikepractice.screens;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;

public class DropScreen extends InventoryScreen {
    public DropScreen(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "drop";
    }

    protected boolean isAcceptable(Item item) {
        return true;
    }

    protected Screen use(Item item) {
        player.drop(item);
        return null;
    }
}
