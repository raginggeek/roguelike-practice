package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;
import com.raginggeek.games.roguelikepractice.service.LevelUpService;
import com.raginggeek.games.roguelikepractice.world.Line;
import com.raginggeek.games.roguelikepractice.world.Point;
import com.raginggeek.games.roguelikepractice.world.Tile;

import java.util.HashMap;
import java.util.Map;

public abstract class CreatureAI {
    protected String name;
    protected Creature creature;
    private Map<String, String> itemNames;

    public CreatureAI(Creature creature) {
        this.name = "creature";
        this.creature = creature;
        this.creature.setCreatureAi(this);
        this.itemNames = new HashMap<>();
    }

    public CreatureAI(Creature creature, String name) {
        this.name = name;
        this.creature = creature;
        this.creature.setCreatureAi(this);
        this.itemNames = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public boolean canSee(int wx, int wy, int wz) {
        if (creature.getZ() != wz) {
            return false; // can't see through floors and ceilings
        }

        if ((creature.getX() - wx) * (creature.getX() - wx)
                + (creature.getY() - wy) * (creature.getY() - wy)
                > creature.getVisionRadius() * creature.getVisionRadius()) {
            return false;
        }

        for (Point p : new Line(creature.getX(), creature.getY(), wx, wy)) {
            if (creature.getRealTile(p.getX(), p.getY(), wz).isGround() || p.getX() == wx && p.getY() == wy) {
                continue;
            }
            return false;
        }
        return true;
    }

    public void onEnter(int x, int y, int z, Tile tile) {
        if (tile.isGround()) {
            creature.setX(x);
            creature.setY(y);
            creature.setZ(z);
        } else {
            creature.doEvent("bump into a wall");
        }
    }

    public void onGainLevel() {
        new LevelUpService().autoLevelUp(creature);
    }

    public void wander() {
        int mx = (int) (Math.random() * 3) - 1;
        int my = (int) (Math.random() * 3) - 1;

        Creature other = creature.getWorldCreature(creature.getX() + mx,
                creature.getY() + my,
                creature.getZ());

        if (other != null && other.getGlyph() == creature.getGlyph()) {
            return;
        } else {
            creature.moveBy(mx, my, 0);
        }
    }

    public Tile getRememberedTile(int wx, int wy, int wz) {
        return Tile.UNKNOWN;
    }

    public abstract void onUpdate();

    public abstract void onNotify(String message);

    public String getName(Item item) {
        String name = itemNames.get(item.getName());
        return name == null ? item.getAppearance() : name;
    }

    public void setName(Item item, String name) {
        itemNames.put(item.getName(), name);
    }

}
