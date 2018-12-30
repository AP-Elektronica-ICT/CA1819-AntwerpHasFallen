package com.ahf.antwerphasfallen.Model;

import java.util.List;

/**
 * Created by Jorren on 16/11/2018.
 */

public class Inventory {
    private int id;
    private List<InventoryItem> ingredients;
    private List<InventoryItem> items;

    public Inventory() {
    }

    public int getId() {
        return id;
    }

    public List<InventoryItem> getIngredients() {
        return ingredients;
    }

    public List<InventoryItem> getItems() {
        return items;
    }
}
