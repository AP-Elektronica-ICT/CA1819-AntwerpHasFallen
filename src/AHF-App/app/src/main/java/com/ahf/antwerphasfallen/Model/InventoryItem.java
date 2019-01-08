package com.ahf.antwerphasfallen.Model;

/**
 * Created by Jorren on 16/11/2018.
 */

public class InventoryItem {
    private int id;
    private int quantity;
    private Item item;

    public InventoryItem() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
