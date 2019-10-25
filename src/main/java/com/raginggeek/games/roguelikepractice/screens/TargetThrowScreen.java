package com.raginggeek.games.roguelikepractice.screens;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;
import com.raginggeek.games.roguelikepractice.world.Line;
import com.raginggeek.games.roguelikepractice.world.Point;

public class TargetThrowScreen extends TargettingScreen {
    private Item item;

    public TargetThrowScreen(Creature player, int sx, int sy, Item item) {
        super(player, "Throw " + item.getName() + " at?", sx, sy);
        this.item = item;
    }

    public boolean isAcceptable(int x, int y) {
        if (!player.canSee(x, y, player.getZ())) {
            return false;
        }

        for (Point p : new Line(player.getX(), player.getY(), x, y)) {
            if (!player.getRealTile(p.getX(), p.getY(), player.getZ()).isGround()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {

    }

    @Override
    public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
        player.throwItem(item, x, y, player.getZ());
    }
}
