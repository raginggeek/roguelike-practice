package com.raginggeek.games.roguelikepractice.entities.effects;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;

public abstract class Effect implements Cloneable {
    protected int duration;

    public Effect(int duration) {
        this.duration = duration;
    }

    public Effect(Effect source) {
        this.duration = source.duration;
    }

    public boolean isDone() {
        return duration < 1;
    }

    public void update(Creature creature) {
        duration--;
    }

    public abstract void start(Creature creature);

    public abstract void end(Creature creature);

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }
}
