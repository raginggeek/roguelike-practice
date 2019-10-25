package com.raginggeek.games.roguelikepractice.screens;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;

public class QuaffScreen extends InventoryScreen {

    public QuaffScreen(Creature player) {
        super(player);
    }

    @Override
    protected String getVerb() {
        return "quaff";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return item.getQuaffEffect() != null;
    }

    @Override
    protected Screen use(Item item) {
        player.quaff(item);
        return null;
    }
}
