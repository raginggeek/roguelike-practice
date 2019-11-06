package com.raginggeek.games.roguelikepractice.entities.items;

import com.raginggeek.games.roguelikepractice.entities.Entity;
import com.raginggeek.games.roguelikepractice.entities.effects.Effect;
import com.raginggeek.games.roguelikepractice.entities.effects.Spell;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Item implements Entity {
    private Effect quaffEffect;
    private List<Spell> writtenSpells;
    private char glyph;
    private Color color;
    private String name;
    private int foodValue;
    private int attackValue;
    private int defenseValue;
    private int thrownAttackValue;
    private int rangedAttackValue;
    private String appearance;

    public Item(char glyph, Color color, String name, String appearance) {
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.appearance = appearance;
        this.thrownAttackValue = 1;
        writtenSpells = new ArrayList<>();
    }

    public void modifyFoodValue(int amount) {
        foodValue += amount;
    }

    public void modifyAttackValue(int amount) {
        attackValue += amount;
    }

    public void modifyDefenseValue(int amount) {
        defenseValue += amount;
    }

    public String getDetails() {
        String details = "";
        if (attackValue != 0) {
            details += "     attack:" + attackValue;
        }
        if (thrownAttackValue != 0) {
            details += "     thrown:" + thrownAttackValue;
        }
        if (defenseValue != 0) {
            details += "     defense:" + defenseValue;
        }
        if (foodValue != 0) {
            details += "     food:" + foodValue;
        }
        return details;
    }

    public void modifyThrownAttackValue(int amount) {
        thrownAttackValue += amount;
    }

    public void modifyRangedAttackValue(int amount) {
        rangedAttackValue += amount;
    }

    public void setQuaffEffect(Effect effect) {
        quaffEffect = effect;
    }

    public void addWrittenSpell(String name, int manaCost, Effect effect) {
        writtenSpells.add(new Spell(name, manaCost, effect));
    }

    public String getAppearance() {
        if (appearance == null) {
            return name;
        } else {
            return appearance;
        }
    }
}
