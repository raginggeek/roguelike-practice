package com.raginggeek.games.roguelikepractice.screens;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;

public class ReadScreen extends InventoryScreen {
    private int sx;
    private int sy;

    public ReadScreen(Creature player, int sx, int sy) {
        super(player);
        this.sx = sx;
        this.sy = sy;
    }

    protected String getVerb() {
        return "read";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return !item.getWrittenSpells().isEmpty();
    }

    @Override
    protected Screen use(Item item) {
        return new ReadSpellScreen(player, sx, sy, item);
    }
}
