package com.raginggeek.games.roguelikepractice.entities.actors;

import com.raginggeek.games.roguelikepractice.entities.Entity;
import com.raginggeek.games.roguelikepractice.entities.actors.ai.CreatureAI;
import com.raginggeek.games.roguelikepractice.entities.actors.capabilities.Inventory;
import com.raginggeek.games.roguelikepractice.entities.items.Item;
import com.raginggeek.games.roguelikepractice.world.Tile;
import com.raginggeek.games.roguelikepractice.world.World;

import java.awt.*;

public class Creature implements Entity {
    private World world;

    private int x;
    private int y;
    private int z;
    private char glyph;
    private String name;
    private Color color;
    private CreatureAI ai;
    private int maxHp;
    private int hp;
    private int attackValue;
    private int defenseValue;
    private int visionRadius;
    private Inventory inventory;

    public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.visionRadius = 9;
        this.inventory = new Inventory(20);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public String getName() {
        return name;
    }

    public void setCreatureAi(CreatureAI ai) {
        this.ai = ai;
        this.name = ai.getName();
    }

    public Color getColor() {
        return color;
    }

    public char getGlyph() {
        return glyph;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void dig(int wx, int wy, int wz) {
        this.world.dig(wx, wy, wz);
    }

    public void moveBy(int mx, int my, int mz) {
        if (mx == 0 && my == 0 && mz == 0) {
            return;
        }
        Tile tile = world.getTile(x + mx, y + my, z + mz);

        if (mz == -1) {
            if (tile == Tile.STAIRS_DOWN) {
                doEvent("wall up the stairs to level %d", z + mz + 1);
            } else {
                doEvent("try to go up but are stopped by the cave ceiling");
                return;
            }
        } else if (mz == 1) {
            if (tile == Tile.STAIRS_UP) {
                doEvent("walk down the stairs to level %d", z + mz + 1);
            } else {
                doEvent("try to go down but are stopped by the cave floor");
                return;
            }
        }

        Creature opponent = world.getCreature(x + mx, y + my, z + mz);

        if (opponent == null) {
            ai.onEnter(x + mx, y + my, z + mz, tile);
        } else {
            attack(opponent);
        }
    }

    public void attack(Creature opponent) {
        int damage = Math.max(0, attackValue - opponent.getDefenseValue());
        damage = (int) (Math.random() * damage) + 1;

        doEvent("attack the %s for %d damage", opponent.getName(), damage);
        opponent.notify("The %s attacks you for %d damage.", opponent.getName(), damage);
        opponent.modifyHp(-damage);
    }

    public void modifyHp(int amount) {
        hp += amount;

        if (hp < 1) {
            doEvent("die");
            world.removeCreature(this);
        }
    }

    public boolean canEnter(int x, int y, int z) {
        return world.canEnter(x, y, z);
    }

    public void update() {
        ai.onUpdate();
    }

    public void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    public void doEvent(String message, Object... params) {
        int r = 9;
        for (int ox = -r; ox < r + 1; ox++) {
            for (int oy = -r; oy < r + 1; oy++) {
                if (ox * ox + oy * oy <= r * r) {
                    Creature other = getWorldCreature(x + ox, y + oy, z);
                    if (other != null) {
                        if (other == this)
                            other.notify("You " + message + ".", params);
                        else if (other.canSee(x, y, z)) {
                            other.notify(String.format(
                                    "The '%s' %s.",
                                    name, makeSecondPerson(message)),
                                    params);
                        }
                    }
                }
            }
        }
    }

    private String makeSecondPerson(String text) {
        String[] words = text.split(" ");
        words[0] = words[0] + "s";

        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(" ");
            builder.append(word);
        }
        return builder.toString().trim();
    }

    public boolean canSee(int wx, int wy, int wz) {
        return ai.canSee(wx, wy, wz);
    }

    public Tile getTile(int wx, int wy, int wz) {
        return world.getTile(wx, wy, wz);
    }

    public int getVisionRadius() {
        return visionRadius;
    }

    public Creature getWorldCreature(int wx, int wy, int wz) {
        return world.getCreature(wx, wy, wz);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void pickup() {
        Item item = world.getItem(x, y, z);

        if (inventory.isFull() || item == null) {
            doEvent("grab at the ground");
        } else {
            doEvent("pickup a %s", item.getName());
            world.remove(x, y, z);
            inventory.add(item);
        }
    }

    public void drop(Item item) {
        doEvent("drop a " + item.getName());
        if (world.addAtEmptySpace(item, x, y, z)) {
            doEvent("drop a " + item.getName());
            inventory.remove(item);
        } else {
            notify("There's nowhere to drop the %s.", item.getName());
        }
    }
}
