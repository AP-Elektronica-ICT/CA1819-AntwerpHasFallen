package com.ahf.antwerphasfallen;

/**
 * Created by Jorren on 16/11/2018.
 */

public abstract class InventoryItem {
    private String name;
    private int quantity;

    public InventoryItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
