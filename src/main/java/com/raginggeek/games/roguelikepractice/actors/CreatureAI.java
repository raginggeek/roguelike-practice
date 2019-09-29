package com.raginggeek.games.roguelikepractice.actors;

import com.raginggeek.games.roguelikepractice.world.Tile;

public abstract class CreatureAI {
    protected Creature creature;

    public CreatureAI(Creature creature) {
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    public abstract void onEnter(int x, int y, Tile tile);

    public abstract void onUpdate();
}
