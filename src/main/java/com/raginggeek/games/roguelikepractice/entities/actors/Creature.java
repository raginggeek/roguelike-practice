package com.raginggeek.games.roguelikepractice.entities.actors;

import com.raginggeek.games.roguelikepractice.entities.Entity;
import com.raginggeek.games.roguelikepractice.entities.actors.ai.CreatureAI;
import com.raginggeek.games.roguelikepractice.entities.actors.capabilities.Inventory;
import com.raginggeek.games.roguelikepractice.entities.effects.Effect;
import com.raginggeek.games.roguelikepractice.entities.items.Item;
import com.raginggeek.games.roguelikepractice.world.Line;
import com.raginggeek.games.roguelikepractice.world.Point;
import com.raginggeek.games.roguelikepractice.world.Tile;
import com.raginggeek.games.roguelikepractice.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Creature implements Entity {
    private World world;
    private CreatureAI ai;
    private List<Effect> effects;

    private int x;
    private int y;
    private int z;
    private char glyph;
    private String name;
    private Color color;

    private int maxHp;
    private int hp;
    private int attackValue;
    private int defenseValue;
    private int visionRadius;
    private Inventory inventory;
    private int maxFood;
    private int food;
    private Item weapon;
    private Item armor;
    private int xp;
    private int level;
    private int regenHpCooldown;
    private int regenHpPer1000;


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
        this.maxFood = 1000;
        this.food = maxFood / 3 * 2;
        this.effects = new ArrayList<>();
    }

    public int getXp() {
        return xp;
    }

    public void modifyXp(int amount) {
        xp += amount;
        notify("You %s %d xp.", amount < 0 ? "lose" : "gain", amount);
        while (xp > (int) (Math.pow(level, 1.5) * 20)) {
            level++;
            doEvent("advance to level %d", level);
            ai.onGainLevel();
            modifyHp(level * 2);
        }
    }

    public int getLevel() {
        return level;
    }

    public int getMaxFood() {
        return maxFood;
    }

    public int getFood() {
        return food;
    }

    public void modifyFood(int amount) {
        food += amount;
        if (food > maxFood) {
            maxFood = maxFood + food / 2;
            food = maxFood;
            notify("You can't believe your stomach can hold that much!");
            modifyHp(-1);
        } else if (food < 1 && isPlayer()) {
            modifyHp(-1000);
        }
    }

    public boolean isPlayer() {
        return glyph == '@';
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getAttackValue() {
        return attackValue +
                (weapon == null ? 0 : weapon.getAttackValue()) +
                (armor == null ? 0 : armor.getAttackValue());
    }

    public int getDefenseValue() {
        return defenseValue +
                (armor == null ? 0 : armor.getDefenseValue()) +
                (weapon == null ? 0 : weapon.getDefenseValue());
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
        modifyFood(-10);
        this.world.dig(wx, wy, wz);
        doEvent("dig");
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
            meleeAttack(opponent);
        }
    }

    public void modifyHp(int amount) {
        hp += amount;

        if (hp > maxHp) {
            hp = maxHp;
        } else if (hp < 1) {
            doEvent("die");
            leaveCorpse();
            world.removeCreature(this);
        }
    }

    public void gainXp(Creature opponent) {
        int amount = opponent.getMaxHp() +
                opponent.getAttackValue() +
                opponent.getDefenseValue()
                - level * 2;
        if (amount > 0) {
            modifyXp(amount);
        }
    }

    /**
     * level up hp effect
     */
    public void gainMaxHp() {
        maxHp += 10;
        hp += 10;
        doEvent("look healthier");
    }

    /**
     * level up attack value effect
     */
    public void gainAttackValue() {
        modifyAttackValue(2);
        doEvent("look stronger");
    }

    /**
     * level up defense value effect
     */
    public void gainDefenseValue() {
        modifyDefenseValue(2);
        doEvent("look tougher");
    }

    /**
     * level up vision effect
     */
    public void gainVision() {
        visionRadius += 1;
        doEvent("look more aware");
    }

    public boolean canEnter(int x, int y, int z) {
        return world.canEnter(x, y, z);
    }

    public void update() {
        modifyFood(-1);
        regenerateHealth();
        updateEffects();
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
        if (canSee(wx, wy, wz)) {
            return getRealTile(wx, wy, wz);
        } else {
            return ai.getRememberedTile(wx, wy, wz);
        }
    }

    public Tile getRealTile(int wx, int wy, int wz) {
        return world.getTile(wx, wy, wz);
    }

    public int getVisionRadius() {
        return visionRadius;
    }

    public Creature getWorldCreature(int wx, int wy, int wz) {
        if (wx >= 0 && wy >= 0 && wz >= 0 && canSee(wx, wy, wz)) {
            return world.getCreature(wx, wy, wz);
        } else {
            return null;
        }

    }

    public Inventory getInventory() {
        return inventory;
    }

    public Item getItem(int wx, int wy, int wz) {
        if (canSee(wx, wy, wz)) {
            return world.getItem(wx, wy, wz);
        } else {
            return null;
        }
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
        if (world.addAtEmptySpace(item, x, y, z)) {
            doEvent("drop a " + item.getName());
            inventory.remove(item);
            unEquip(item);
        } else {
            notify("There's nowhere to drop the %s.", item.getName());
        }
    }

    public void leaveCorpse() {
        Item corpse = new Item('%', color, name + " corpse");
        corpse.modifyFoodValue(maxHp * 3);
        world.addAtEmptySpace(corpse, x, y, z);
        for (Item item : inventory.getItems()) {
            if (item != null) {
                drop(item);
            }
        }
    }

    public Item getWeapon() {
        return weapon;
    }

    public Item getArmor() {
        return armor;
    }

    public void unEquip(Item item) {
        if (item != null) {
            if (item == armor) {
                doEvent("unequipped a " + item.getName());
            } else if (item == weapon) {
                doEvent("put away a " + item.getName());
                weapon = null;
            }
        }
    }

    public void equip(Item item) {
        if (!inventory.contains(item)) {
            if (inventory.isFull()) {
                notify("Can't equip %s since you're holding too much stuff", item.getName());
            } else {
                world.removeItem(item);
                inventory.add(item);
            }
        }
        if (item.getAttackValue() != 0 || item.getDefenseValue() != 0) {
            if (item.getAttackValue() >= item.getDefenseValue()) {
                unEquip(weapon);
                doEvent("wield a " + item.getName());
                weapon = item;
            } else {
                unEquip(armor);
                doEvent("put on a " + item.getName());
                armor = item;
            }
        }
    }

    public String getDetails() {
        return String.format("     level:%d     attack:%d     defense:%d     hp:%d", getLevel(), getAttackValue(), getDefenseValue(), getHp());
    }

    public void throwItem(Item item, int wx, int wy, int wz) {
        Point end = new Point(x, y, 0);

        for (Point p : new Line(x, y, wx, wy)) {
            if (!getRealTile(p.getX(), p.getY(), z).isGround()) {
                break;
            }
            end = p;
        }

        wx = end.getX();
        wy = end.getY();

        Creature c = getWorldCreature(wx, wy, wz);
        if (c != null) {
            throwAttack(item, c);
        } else {
            doEvent("throw a %s", item.getName());
        }

        if (item.getQuaffEffect() != null && c != null) {
            getRidOfItem(item);
        } else {
            putItemAt(item, wx, wy, wz);
        }

    }

    public void meleeAttack(Creature opponent) {
        attack(opponent, 3,
                getAttackValue(),
                "attack the %s for %d damage",
                opponent.getName());
    }

    private void throwAttack(Item item, Creature opponent) {
        attack(opponent, 2,
                attackValue / 2 + item.getThrownAttackValue(),
                "throw a %s at the %s for %d damage",
                item.getName(),
                opponent.getName());
        opponent.addEffect(item.getQuaffEffect());
    }

    public void rangedWeaponAttack(Creature opponent) {
        attack(opponent, 2,
                attackValue / 2 + weapon.getRangedAttackValue(),
                "fire a %s at the %s for %d damage",
                weapon.getName(),
                opponent.getName());
    }

    private void attack(Creature opponent, int foodConsumption, int attack, String action, Object... params) {
        modifyFood(-foodConsumption);
        int amount = Math.max(0, attack - opponent.getDefenseValue());
        amount = (int) (Math.random() * amount) + 1;

        Object[] params2 = new Object[params.length + 1];
        System.arraycopy(params, 0, params2, 0, params.length);
        params2[params2.length - 1] = amount;
        doEvent(action, params2);
        opponent.modifyHp(-amount);

        if (opponent.getHp() < 1) {
            gainXp(opponent);
        }
    }


    private void getRidOfItem(Item item) {
        inventory.remove(item);
        unEquip(item);
    }

    private void putItemAt(Item item, int wx, int wy, int wz) {
        getRidOfItem(item);
        world.addAtEmptySpace(item, wx, wy, wz);
    }

    public void modifyRegenHpPer1000(int amount) {
        regenHpPer1000 += amount;
    }

    private void regenerateHealth() {
        regenHpCooldown -= regenHpPer1000;
        if (regenHpCooldown < 0) {
            modifyHp(1);
            modifyFood(-1);
            regenHpCooldown += 1000;
        }
    }

    public void modifyAttackValue(int amount) {
        attackValue += amount;
    }

    public void modifyDefenseValue(int amount) {
        defenseValue += amount;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void quaff(Item item) {
        doEvent("quaff a " + item.getName());
        consume(item);
    }

    public void eat(Item item) {
        doEvent("eat a " + item.getName());
        consume(item);
    }

    public void consume(Item item) {
        if (item.getFoodValue() < 0) {
            notify("Gross!");
        }
        addEffect(item.getQuaffEffect());
        modifyFood(item.getFoodValue());
        getRidOfItem(item);
    }

    private void addEffect(Effect effect) {
        if (effect == null) {
            return;
        }

        effect.start(this);
        effects.add(effect);
    }

    private void updateEffects() {
        List<Effect> done = new ArrayList<>();
        for (Effect effect : effects) {
            effect.update(this);
            if (effect.isDone()) {
                effect.end(this);
                done.add(effect);
            }
        }
        effects.removeAll(done);
    }

}
