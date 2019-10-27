package com.raginggeek.games.roguelikepractice.entities.items;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.actors.CreatureFactory;
import com.raginggeek.games.roguelikepractice.entities.effects.Effect;
import com.raginggeek.games.roguelikepractice.world.World;

public class ItemFactory {
    private World world;
    private CreatureFactory creatureFactory;

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
            case 2:
                return newPotionOfMana(depth);
            default:
                return newPotionofWarrior(depth);
        }
    }

    public Item randomSpellBook(int depth) {
        switch ((int) (Math.random() * 3)) {
            case 0:
                return newBlueMagesSpellbook(depth);
            default:
                return newWhiteMagesSpellbook(depth);
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

    public Item newPotionOfMana(int depth) {
        Item item = new Item('!', AsciiPanel.white, "mana potion");
        item.setQuaffEffect(new Effect(1) {
            public void start(Creature creature) {
                if (creature.getMana() == creature.getMaxMana()) {
                    return;
                }
                creature.modifyMana(15);
                creature.doEvent("look more concentrated");
            }

            public void end(Creature creature) {
            }
        });
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newWhiteMagesSpellbook(int depth) {
        Item item = new Item('+', AsciiPanel.brightWhite, "white mage's spellbook");
        item.addWrittenSpell("minor heal", 4, new Effect(1) {
            @Override
            public void start(Creature creature) {
                if (creature.getHp() == creature.getMaxHp()) {
                    return;
                }

                creature.modifyHp(20);
                creature.doEvent("look healthier");
            }

            @Override
            public void end(Creature creature) {
            }
        });
        item.addWrittenSpell("major heal", 8, new Effect(1) {
            @Override
            public void start(Creature creature) {
                if (creature.getHp() == creature.getMaxHp()) {
                    return;
                }

                creature.modifyHp(50);
                creature.doEvent("look healthier");
            }

            @Override
            public void end(Creature creature) {

            }
        });
        item.addWrittenSpell("slow heal", 12, new Effect(50) {
            @Override
            public void start(Creature creature) {

            }

            @Override
            public void end(Creature creature) {

            }

            @Override
            public void update(Creature creature) {
                super.update(creature);
                creature.modifyHp(2);
            }
        });
        item.addWrittenSpell("inner strength", 16, new Effect(50) {
            @Override
            public void start(Creature creature) {
                creature.modifyAttackValue(2);
                creature.modifyDefenseValue(2);
                creature.modifyRegenManaPer1000(-10);
                creature.modifyRegenHpPer1000(10);
                creature.modifyVisionRadius(1);
                creature.doEvent("seem to glow with inner strength");
            }

            public void update(Creature creature) {
                super.update(creature);
                if (Math.random() < 0.25) {
                    creature.modifyHp(1);
                }
            }

            @Override
            public void end(Creature creature) {
                creature.modifyAttackValue(-2);
                creature.modifyDefenseValue(-2);
                creature.modifyRegenHpPer1000(-10);
                creature.modifyRegenManaPer1000(10);
                creature.modifyVisionRadius(-1);
                creature.doEvent("the glow wears off");
            }
        });
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newBlueMagesSpellbook(int depth) {
        Item item = new Item('+', AsciiPanel.brightBlue, "blue mage's spellbook");
        item.addWrittenSpell("blood to mana", 1, new Effect(1) {
            public void start(Creature creature) {
                int amount = Math.min(creature.getHp() - 1, creature.getMaxMana() - creature.getMana());
                creature.modifyHp(-amount);
                creature.modifyMana(amount);
            }

            @Override
            public void end(Creature creature) {

            }
        });
        item.addWrittenSpell("blink", 6, new Effect(1) {
            public void start(Creature creature) {
                creature.doEvent("fade out");
                int mx = 0;
                int my = 0;

                do {
                    mx = (int) (Math.random() * 11) - 5;
                    my = (int) (Math.random() * 11) - 5;
                } while (!creature.canEnter(creature.getX() + mx, creature.getY() + my, creature.getZ()) &&
                        creature.canSee(creature.getX() + mx, creature.getY() + my, creature.getZ()));

                creature.moveBy(mx, my, 0);
                creature.doEvent("fade in");
            }

            @Override
            public void end(Creature creature) {

            }
        });

        item.addWrittenSpell("summon bats", 11, new Effect(1) {
            @Override
            public void start(Creature creature) {
                creatureFactory = new CreatureFactory(world);
                for (int ox = -1; ox < 2; ox++) {
                    for (int oy = -1; oy < 2; oy++) {
                        int nx = creature.getX() + ox;
                        int ny = creature.getY() + oy;
                        if (ox == 0 && oy == 0 || creature.getWorldCreature(nx, ny, creature.getZ()) != null) {
                            continue;
                        }

                        Creature bat = creatureFactory.newBat(0);

                        if (!bat.canEnter(nx, ny, creature.getZ())) {
                            world.removeCreature(bat);
                            continue;
                        }

                        bat.setX(nx);
                        bat.setY(ny);
                        bat.setZ(creature.getZ());

                        creature.summon(bat);

                    }
                }
            }

            @Override
            public void end(Creature creature) {

            }
        });
        item.addWrittenSpell("detect creatures", 16, new Effect(75) {
            @Override
            public void start(Creature creature) {
                creature.doEvent("look far off into the distance");
                creature.modifyDetectCreatures(1);
            }

            @Override
            public void end(Creature creature) {
                creature.modifyDetectCreatures(-1);
            }
        });
        world.addAtEmptyLocation(item, depth);
        return item;
    }


}
