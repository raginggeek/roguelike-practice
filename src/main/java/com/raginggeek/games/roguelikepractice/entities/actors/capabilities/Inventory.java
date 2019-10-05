package com.raginggeek.games.roguelikepractice.entities.actors.capabilities;

import com.raginggeek.games.roguelikepractice.entities.items.Item;

public class Inventory {
    private Item[] items;

    public Inventory(int max) {
        items = new Item[max];
    }

    public Item[] getItems() {
        return items;
    }

    public Item get(int i) {
        return items[i];
    }

    public void add(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                break;
            }
        }
    }

    public void remove(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                items[i] = null;
                return;
            }
        }
    }

    public boolean isFull() {
        int size = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                size++;
            }
        }
        return size == items.length;
    }
}
