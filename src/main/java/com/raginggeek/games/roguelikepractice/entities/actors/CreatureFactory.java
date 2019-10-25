package com.raginggeek.games.roguelikepractice.entities.actors;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.entities.actors.ai.*;
import com.raginggeek.games.roguelikepractice.entities.actors.capabilities.FieldOfView;
import com.raginggeek.games.roguelikepractice.entities.items.ItemFactory;
import com.raginggeek.games.roguelikepractice.world.World;

import java.util.List;

public class CreatureFactory {
    private World world;
    private ItemFactory equipmentFactory;

    public CreatureFactory(World world) {
        this.world = world;
        this.equipmentFactory = new ItemFactory(world);
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

    public Creature newGoblin(int depth, Creature player) {
        Creature goblin = new Creature(world, 'g', AsciiPanel.brightGreen, 66, 15, 5);
        new GoblinAI(goblin, player);
        goblin.equip(equipmentFactory.randomWeapon(depth));
        goblin.equip(equipmentFactory.randomArmor(depth));
        world.addAtEmptyLocation(goblin, depth);

        return goblin;
    }

}
