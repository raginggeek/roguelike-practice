package com.raginggeek.games.roguelikepractice.service;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.actors.capabilities.LevelUpOption;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LevelUpService {
    //TODO: consider updating to use a sqlite database?
    private static LevelUpOption[] options = new LevelUpOption[]{
            new LevelUpOption("Increased hit points") {
                @Override
                public void invoke(Creature creature) {
                    creature.gainMaxHp();
                }
            },
            new LevelUpOption("Increased attack value") {
                @Override
                public void invoke(Creature creature) {
                    creature.gainAttackValue();
                }
            },
            new LevelUpOption("Increased defense value") {
                @Override
                public void invoke(Creature creature) {
                    creature.gainDefenseValue();
                }
            },
            new LevelUpOption("Increased vision") {
                @Override
                public void invoke(Creature creature) {
                    creature.gainVision();
                }
            },
            new LevelUpOption("Increased mana") {
                @Override
                public void invoke(Creature creature) {
                    creature.gainMaxMana();
                }
            },
            new LevelUpOption("Increased mana regeneration") {
                @Override
                public void invoke(Creature creature) {
                    creature.gainRegenMana();
                }
            }
    };

    public void autoLevelUp(Creature creature) {
        options[(int) (Math.random() * options.length)].invoke(creature);
    }

    public List<String> getLevelUpOptions() {
        return Arrays.stream(options).map(LevelUpOption::getName).collect(Collectors.toList());
    }

    public LevelUpOption getLevelUpOption(String name) {
        //TODO: cleanup this messy use of Optional.get without check, could result in a NPE.
        return Arrays.stream(options).filter(option -> option.getName().equals(name)).findFirst().get();
    }
}
