package com.raginggeek.games.roguelikepractice.entities.effects;

public class Spell {
    private String name;
    private int manaCost;
    private Effect effect;

    public Spell(String name, int manaCost, Effect effect) {
        this.name = name;
        this.manaCost = manaCost;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public Effect getEffect() {
        return (Effect) effect.clone();
    }
}
