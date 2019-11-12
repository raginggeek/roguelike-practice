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
        List<Point> points = new Path(creature, target.getLocation().getX(), target.getLocation().getY()).getPoints();
        int mx = points.get(0).getX() - creature.getLocation().getX();
        int my = points.get(0).getY() - creature.getLocation().getY();

        creature.moveBy(new Point(mx, my, 0));
    }

    protected boolean canRangedWeaponAttack(Creature opponent) {
        return creature.getWeapon() != null &&
                creature.getWeapon().getRangedAttackValue() > 0 &&
                creature.canSee(opponent.getLocation());
    }

    protected boolean canThrowAt(Creature opponent) {
        return creature.canSee(opponent.getLocation()) &&
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
        return creature.getItem(creature.getLocation()) != null &&
                !creature.getInventory().isFull();
    }
}
