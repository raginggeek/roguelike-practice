package com.raginggeek.games.roguelikepractice.screens;

import com.raginggeek.games.roguelikepractice.entities.actors.Creature;
import com.raginggeek.games.roguelikepractice.entities.items.Item;

public class ExamineScreen extends InventoryScreen {

    public ExamineScreen(Creature player) {
        super(player);
    }

    @Override
    protected String getVerb() {
        return "examine";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return true;
    }

    @Override
    protected Screen use(Item item) {
        String article = "aeiou".contains(player.nameOf(item).subSequence(0, 1)) ? "an " : "a ";
        player.notify("It's " + article + player.nameOf(item) + ". " + item.getDetails());
        return null;
    }
}
