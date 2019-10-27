package com.raginggeek.games.roguelikepractice.screens;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.effects.Spell;

public class CastSpellScreen extends TargettingScreen {
    private Spell spell;

    public CastSpellScreen(Creature player, String caption, int sx, int sy, Spell spell) {
        super(player, caption, sx, sy);
        this.spell = spell;
    }


    @Override
    public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {

    }

    @Override
    public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
        player.castSpell(spell, x, y);
    }
}
