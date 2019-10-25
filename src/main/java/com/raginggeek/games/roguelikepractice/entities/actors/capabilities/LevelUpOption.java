package com.raginggeek.games.roguelikepractice.entities.actors.capabilities;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;

public abstract class LevelUpOption {
    private String name;

    public LevelUpOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void invoke(Creature creature);
}
