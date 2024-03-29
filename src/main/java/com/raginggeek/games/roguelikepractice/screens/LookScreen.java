package com.raginggeek.games.roguelikepractice.screens;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;
import com.raginggeek.games.roguelikepractice.world.Point;
import com.raginggeek.games.roguelikepractice.world.Tile;

public class LookScreen extends TargettingScreen {

    public LookScreen(Creature player, String caption, int sx, int sy) {
        super(player, caption, sx, sy);
    }

    @Override
    public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
        //TODO: fix OutOfBounds by restricting vision to game screen dimensions.
        Creature creature = player.getWorldCreature(new Point(x, y, player.getLocation().getZ()));
        if (creature != null) {
            caption = creature.getGlyph() + " " + creature.getName() + creature.getDetails();
            return;
        }

        Item item = player.getItem(new Point(x, y, player.getLocation().getZ()));
        if (item != null) {
            caption = item.getGlyph() + " " + player.nameOf(item) + item.getDetails();
            return;
        }

        Tile tile = player.getTile(new Point(x, y, player.getLocation().getZ()));
        caption = tile.getGlyph() + " " + tile.getDetails();
    }

    @Override
    public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {

    }
}
