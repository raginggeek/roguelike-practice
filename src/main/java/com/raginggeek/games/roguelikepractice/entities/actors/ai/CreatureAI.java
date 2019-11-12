package com.raginggeek.games.roguelikepractice.entities.actors.ai;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;
import com.raginggeek.games.roguelikepractice.service.LevelUpService;
import com.raginggeek.games.roguelikepractice.world.Line;
import com.raginggeek.games.roguelikepractice.world.Point;
import com.raginggeek.games.roguelikepractice.world.Tile;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
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

    public boolean canSee(Point targetLocation) {
        if (creature.getLocation().getZ() != targetLocation.getZ()) {
            return false; // can't see through floors and ceilings
        }

        if ((creature.getLocation().getX() - targetLocation.getX()) * (creature.getLocation().getX() - targetLocation.getX())
                + (creature.getLocation().getY() - targetLocation.getY()) * (creature.getLocation().getY() - targetLocation.getY())
                > creature.getVisionRadius() * creature.getVisionRadius()) {
            return false;
        }

        for (Point p : new Line(creature.getLocation().getX(), creature.getLocation().getY(), targetLocation.getX(), targetLocation.getY())) {
            p.setZ(targetLocation.getZ());
            if (creature.getRealTile(p).isGround() || p.getX() == targetLocation.getX() && p.getY() == targetLocation.getY()) {
                continue;
            }
            return false;
        }
        return true;
    }

    public void onEnter(Point location, Tile tile) {
        if (tile.isGround()) {
            creature.setLocation(location);
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

        Point vector = new Point(mx, my, 0);
        Creature other = creature.getWorldCreature(creature.getLocation().add(vector));

        if (other != null && other.getGlyph() == creature.getGlyph()) {
            return;
        } else {
            creature.moveBy(vector);
        }
    }

    public Tile getRememberedTile(Point rememberedTile) {
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
