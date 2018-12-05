package com.ahf.antwerphasfallen;

import java.util.List;

/**
 * Created by Jorren on 16/11/2018.
 */

public class Inventory {
    private int id;
    private List<InventoryItem> ingredients;
    private List<InventoryItem> shopItems;

    public Inventory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<InventoryItem> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<InventoryItem> ingredients) {
        this.ingredients = ingredients;
    }

    public List<InventoryItem> getShopItems() {
        return shopItems;
    }

    public void setShopItems(List<InventoryItem> shopItems) {
        this.shopItems = shopItems;
    }
}
