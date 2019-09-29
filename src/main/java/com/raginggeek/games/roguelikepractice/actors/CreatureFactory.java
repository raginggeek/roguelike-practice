package com.raginggeek.games.roguelikepractice.actors;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.world.World;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer() {
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite);
        world.addAtEmptyLocation(player);
        new PlayerAI(player);
        return player;
    }

    public Creature newFungus() {
        Creature fungus = new Creature(world, 'f', AsciiPanel.green);
        world.addAtEmptyLocation(fungus);
        new FungusAI(fungus, this);
        return fungus;
    }

}
