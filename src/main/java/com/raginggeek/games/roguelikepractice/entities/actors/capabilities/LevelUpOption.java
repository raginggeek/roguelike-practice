package com.raginggeek.games.roguelikepractice.entities.actors.capabilities;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import lombok.Getter;

@Getter
public abstract class LevelUpOption {
    private String name;

    public LevelUpOption(String name) {
        this.name = name;
    }

    public abstract void invoke(Creature creature);
}
