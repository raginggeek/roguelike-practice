package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.actors.CreatureFactory;
import com.raginggeek.games.roguelikepractice.world.Tile;

public class FungusAI extends CreatureAI {
    private CreatureFactory creatureFactory;
    private int spreadCount;

    public FungusAI(Creature creature, CreatureFactory creatureFactory) {
        super(creature, "fungus");
        this.creatureFactory = creatureFactory;
    }

    public void onEnter(int x, int y, int z, Tile tile) {
    } //fungi don't move

    public void onUpdate() {
        if (spreadCount < 2 && Math.random() < 0.02) {
            spread();
        }
    }

    public void onNotify(String message) {
    }

    private void spread() {
        int x = creature.getLocation().getX() + (int) (Math.random() * 11) - 5;
        int y = creature.getLocation().getY() + (int) (Math.random() * 11) - 5;
        int z = creature.getLocation().getZ();

        if (creature.canEnter(x, y, z)) {
            Creature child = creatureFactory.newFungus(z);
            child.getLocation().setX(x);
            child.getLocation().setY(y);
            spreadCount++;
            creature.doEvent("spawn a child");
        }
    }
}
