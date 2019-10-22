package com.raginggeek.games.roguelikepractice.entities.actors;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.entities.actors.ai.BatAI;
import com.raginggeek.games.roguelikepractice.entities.actors.ai.FungusAI;
import com.raginggeek.games.roguelikepractice.entities.actors.ai.PlayerAI;
import com.raginggeek.games.roguelikepractice.entities.actors.ai.ZombieAI;
import com.raginggeek.games.roguelikepractice.entities.actors.capabilities.FieldOfView;
import com.raginggeek.games.roguelikepractice.world.World;

import java.util.List;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer(List<String> messages, FieldOfView fov) {
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);
        world.addAtEmptyLocation(player, 0);
        new PlayerAI(player, messages, fov);
        return player;
    }

    public Creature newFungus(int depth) {
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0);
        world.addAtEmptyLocation(fungus, depth);
        new FungusAI(fungus, this);
        return fungus;
    }

    public Creature newBat(int depth) {
        Creature bat = new Creature(world, 'b', AsciiPanel.yellow, 15, 5, 0);
        world.addAtEmptyLocation(bat, depth);
        new BatAI(bat);
        return bat;
    }

    public Creature newZombie(int depth, Creature player) {
        Creature zombie = new Creature(world, 'z', AsciiPanel.white, 50, 10, 10);
        world.addAtEmptyLocation(zombie, depth);
        new ZombieAI(zombie, player);
        return zombie;
    }

}
