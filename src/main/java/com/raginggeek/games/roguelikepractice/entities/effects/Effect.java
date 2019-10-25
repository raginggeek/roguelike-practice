package com.raginggeek.games.roguelikepractice.entities.effects;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;

public abstract class Effect {
    protected int duration;

    public Effect(int duration) {
        this.duration = duration;
    }

    public boolean isDone() {
        return duration < 1;
    }

    public void update(Creature creature) {
        duration--;
    }

    public abstract void start(Creature creature);

    public abstract void end(Creature creature);


}
