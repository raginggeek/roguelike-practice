package com.raginggeek.games.roguelikepractice.entities.items;

import com.raginggeek.games.roguelikepractice.entities.Entity;
import com.raginggeek.games.roguelikepractice.entities.effects.Effect;

import java.awt.*;

public class Item implements Entity {
    private Effect quaffEffect;
    private char glyph;
    private Color color;
    private String name;
    private int foodValue;
    private int attackValue;
    private int defenseValue;
    private int thrownAttackValue;
    private int rangedAttackValue;

    public Item(char glyph, Color color, String name) {
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.thrownAttackValue = 1;
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getFoodValue() {
        return foodValue;
    }

    public void modifyFoodValue(int amount) {
        foodValue += amount;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void modifyAttackValue(int amount) {
        attackValue += amount;
    }

    public int getDefenseValue() {
        return defenseValue;
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

    public int getThrownAttackValue() {
        return thrownAttackValue;
    }

    public void modifyThrownAttackValue(int amount) {
        thrownAttackValue += amount;
    }

    public int getRangedAttackValue() {
        return rangedAttackValue;
    }

    public void modifyRangedAttackValue(int amount) {
        rangedAttackValue += amount;
    }

    public Effect getQuaffEffect() {
        return quaffEffect;
    }

    public void setQuaffEffect(Effect effect) {
        quaffEffect = effect;
    }
}
