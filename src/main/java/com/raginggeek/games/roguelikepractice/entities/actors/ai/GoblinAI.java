package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;

public class GoblinAI extends SentientAggressiveCreatureAI {
    private Creature player;

    public GoblinAI(Creature creature, Creature player) {
        super(creature, "goblin");
        this.player = player;
    }

    @Override
    public void onUpdate() {

        if (canRangedWeaponAttack(player)) {
            creature.rangedWeaponAttack(player);
        } else if (canThrowAt(player)) {
            creature.throwItem(getWeaponToThrow(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        } else if (creature.canSee(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ())) {
            hunt(player);
        } else if (canPickup()) {
            creature.pickup();
        } else if (canUseBetterEquipment()) {
            useBetterEquipment();
        } else {
            wander();
        }

    }

    @Override
    public void onNotify(String message) {

    }


}
