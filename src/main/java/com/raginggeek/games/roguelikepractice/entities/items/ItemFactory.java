package com.raginggeek.games.roguelikepractice.entities.items;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.effects.Effect;
import com.raginggeek.games.roguelikepractice.world.World;

public class ItemFactory {
    private World world;

    public ItemFactory(World world) {
        this.world = world;
    }

    public Item newRock(int depth) {
        Item rock = new Item(',', AsciiPanel.yellow, "rock");
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }

    public Item newDagger(int depth) {
        Item item = new Item(')', AsciiPanel.white, "dagger");
        item.modifyAttackValue(5);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newSword(int depth) {
        Item item = new Item(')', AsciiPanel.brightWhite, "sword");
        item.modifyAttackValue(10);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newStaff(int depth) {
        Item item = new Item(')', AsciiPanel.yellow, "staff");
        item.modifyAttackValue(5);
        item.modifyDefenseValue(3);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newLightArmor(int depth) {
        Item item = new Item('[', AsciiPanel.green, "tunic");
        item.modifyDefenseValue(2);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newMediumArmor(int depth) {
        Item item = new Item('[', AsciiPanel.white, "chainmail");
        item.modifyDefenseValue(4);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newHeavyArmor(int depth) {
        Item item = new Item('[', AsciiPanel.brightWhite, "platemail");
        item.modifyDefenseValue(6);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item randomWeapon(int depth) {
        switch ((int) (Math.random() * 3)) {
            case 0:
                return newDagger(depth);
            case 1:
                return newSword(depth);
            case 2:
                return newBow(depth);
            default:
                return newStaff(depth);
        }
    }

    public Item randomArmor(int depth) {
        switch ((int) (Math.random() * 3)) {
            case 0:
                return newLightArmor(depth);
            case 1:
                return newMediumArmor(depth);
            default:
                return newHeavyArmor(depth);
        }
    }

    public Item randomPotion(int depth) {
        switch ((int) (Math.random() * 3)) {
            case 0:
                return newPotionOfHealth(depth);
            case 1:
                return newPotionOfPoison(depth);
            default:
                return newPotionofWarrior(depth);
        }
    }

    public Item newBow(int depth) {
        Item item = new Item(')', AsciiPanel.yellow, "bow");
        item.modifyAttackValue(1);
        item.modifyRangedAttackValue(5);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newMcGuffin(int depth) {
        Item item = new Item('*', AsciiPanel.brightWhite, "Holy Relic");
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newPotionOfHealth(int depth) {
        Item item = new Item('!', AsciiPanel.white, "health potion");
        item.setQuaffEffect(new Effect(1) {
            public void start(Creature creature) {
                if (creature.getHp() == creature.getMaxHp()) {
                    return;
                }
                creature.modifyHp(15);
                creature.doEvent("look healthier");
            }

            public void end(Creature creature) {
            }
        });
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newPotionOfPoison(int depth) {
        Item item = new Item('!', AsciiPanel.white, "poison potion");
        item.setQuaffEffect(new Effect(20) {
            @Override
            public void start(Creature creature) {
                creature.doEvent("look sick");
            }

            @Override
            public void update(Creature creature) {
                super.update(creature);
                creature.modifyHp(-1);
            }

            @Override
            public void end(Creature creature) {

            }
        });
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newPotionofWarrior(int depth) {
        Item item = new Item('!', AsciiPanel.white, "warrior's potion");
        item.setQuaffEffect(new Effect(20) {
            @Override
            public void start(Creature creature) {
                creature.modifyAttackValue(5);
                creature.modifyDefenseValue(5);
                creature.doEvent("look stronger");
            }

            @Override
            public void end(Creature creature) {
                creature.modifyAttackValue(-5);
                creature.modifyDefenseValue(-5);
                creature.doEvent("look less strong");
            }
        });
        world.addAtEmptyLocation(item, depth);
        return item;
    }
}
