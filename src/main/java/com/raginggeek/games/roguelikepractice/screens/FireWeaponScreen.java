package com.raginggeek.games.roguelikepractice.screens;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.world.Line;
import com.raginggeek.games.roguelikepractice.world.Point;

public class FireWeaponScreen extends TargettingScreen {

    public FireWeaponScreen(Creature player, int sx, int sy) {
        super(player, "Fire " + player.getWeapon().getName() + " at?", sx, sy);
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
        Creature opponent = player.getWorldCreature(x, y, player.getZ());

        if (opponent == null) {
            player.notify("There's no one there to fire at.");
        } else {
            player.rangedWeaponAttack(opponent);
        }
    }
}
