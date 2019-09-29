package com.raginggeek.games.roguelikepractice.actors;

import com.raginggeek.games.roguelikepractice.world.Tile;

public class FungusAI extends CreatureAI {
    private CreatureFactory creatureFactory;
    private int spreadCount;

    public FungusAI(Creature creature, CreatureFactory creatureFactory) {
        super(creature);
        this.creatureFactory = creatureFactory;
    }

    public void onEnter(int x, int y, Tile tile) {
    } //fungi don't move

    public void onUpdate() {
        if (spreadCount < 5 && Math.random() < 0.02) {
            spread();
        }
    }

    private void spread() {
        int x = creature.getX() + (int) (Math.random() * 11) - 5;
        int y = creature.getY() + (int) (Math.random() * 11) - 5;

        if (creature.canEnter(x, y)) {
            Creature child = creatureFactory.newFungus();
            child.setX(x);
            child.setY(y);
            spreadCount++;
        }
    }
}
