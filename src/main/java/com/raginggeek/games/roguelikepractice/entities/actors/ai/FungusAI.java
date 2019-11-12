package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.actors.CreatureFactory;
import com.raginggeek.games.roguelikepractice.world.Point;
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
        Point spawnVector = new Point((int) (Math.random() * 11) - 5, (int) (Math.random() * 11) - 5, 0);
        Point spawnPoint = creature.getLocation().add(spawnVector);

        if (creature.canEnter(spawnPoint)) {
            Creature child = creatureFactory.newFungus(spawnPoint.getZ());
            child.getLocation().setX(spawnPoint.getX());
            child.getLocation().setY(spawnPoint.getY());
            spreadCount++;
            creature.doEvent("spawn a child");
        }
    }
}
