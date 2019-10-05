package com.raginggeek.games.roguelikepractice.entities.items;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.world.World;

public class ItemFactory {
    private World world;

    public ItemFactory(World world) {
        this.world = world;
    }

    public Item newRock(int depth) {
        Item rock = new Item(',', AsciiPanel.yellow, "rock");
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }

    public Item newMcGuffin(int depth) {
        Item item = new Item('*', AsciiPanel.brightWhite, "Holy Relic");
        world.addAtEmptyLocation(item, depth);
        return item;
    }
}
