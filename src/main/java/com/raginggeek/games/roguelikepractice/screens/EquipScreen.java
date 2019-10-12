package com.raginggeek.games.roguelikepractice.screens;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;

public class EquipScreen extends InventoryScreen {

    public EquipScreen(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "wear or wield";
    }

    protected boolean isAcceptable(Item item) {
        return item.getAttackValue() > 0 || item.getDefenseValue() > 0;
    }

    protected Screen use(Item item) {
        player.equip(item);
        return null;
    }
}
