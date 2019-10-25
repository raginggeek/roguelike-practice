package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;

public abstract class SentientAggressiveCreatureAI extends AggressiveCreatureAI {

    public SentientAggressiveCreatureAI(Creature creature) {
        super(creature);
    }

    public SentientAggressiveCreatureAI(Creature creature, String name) {
        super(creature, name);
    }

    protected boolean canUseBetterEquipment() {
        int currentWeaponRating = creature.getWeapon() == null ? 0 : creature.getWeapon().getAttackValue() + creature.getWeapon().getRangedAttackValue();
        int currentArmorRating = creature.getArmor() == null ? 0 : creature.getArmor().getDefenseValue();
        for (Item item : creature.getInventory().getItems()) {
            if (item == null) {
                continue;
            }

            boolean isArmor = item.getAttackValue() + item.getRangedAttackValue() < item.getDefenseValue();
            if (item.getAttackValue() + item.getRangedAttackValue() > currentWeaponRating ||
                    isArmor && item.getDefenseValue() > currentArmorRating) {
                return true;
            }
        }
        return false;
    }

    protected void useBetterEquipment() {
        int currentWeaponRating = creature.getWeapon() == null ? 0 : creature.getWeapon().getAttackValue() + creature.getWeapon().getRangedAttackValue();
        int currentArmorRating = creature.getArmor() == null ? 0 : creature.getArmor().getDefenseValue();
        for (Item item : creature.getInventory().getItems()) {
            if (item == null) {
                continue;
            }

            boolean isArmor = item.getAttackValue() + item.getRangedAttackValue() < item.getDefenseValue();
            if (item.getAttackValue() + item.getRangedAttackValue() > currentWeaponRating ||
                    isArmor && item.getDefenseValue() > currentArmorRating) {
                creature.equip(item);
            }
        }
    }
}
