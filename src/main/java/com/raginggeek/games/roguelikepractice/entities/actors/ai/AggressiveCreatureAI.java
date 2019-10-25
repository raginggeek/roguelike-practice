package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;
import com.raginggeek.games.roguelikepractice.world.Path;
import com.raginggeek.games.roguelikepractice.world.Point;

import java.util.List;

public abstract class AggressiveCreatureAI extends CreatureAI {

    public AggressiveCreatureAI(Creature creature) {
        super(creature);
    }

    public AggressiveCreatureAI(Creature creature, String name) {
        super(creature, name);
    }

    public void hunt(Creature target) {
        List<Point> points = new Path(creature, target.getX(), target.getY()).getPoints();
        int mx = points.get(0).getX() - creature.getX();
        int my = points.get(0).getY() - creature.getY();

        creature.moveBy(mx, my, 0);
    }

    protected boolean canRangedWeaponAttack(Creature opponent) {
        return creature.getWeapon() != null &&
                creature.getWeapon().getRangedAttackValue() > 0 &&
                creature.canSee(opponent.getX(), opponent.getY(), opponent.getZ());
    }

    protected boolean canThrowAt(Creature opponent) {
        return creature.canSee(opponent.getX(), opponent.getY(), opponent.getZ()) &&
                getWeaponToThrow() != null;
    }

    protected Item getWeaponToThrow() {
        Item toThrow = null;
        for (Item item : creature.getInventory().getItems()) {
            if (item == null ||
                    creature.getWeapon() == item ||
                    creature.getArmor() == item) {
                continue;
            }

            if (toThrow == null || item.getThrownAttackValue() > toThrow.getThrownAttackValue()) {
                toThrow = item;
            }
        }
        return toThrow;
    }

    protected boolean canPickup() {
        return creature.getItem(creature.getX(), creature.getY(), creature.getZ()) != null &&
                !creature.getInventory().isFull();
    }
}
