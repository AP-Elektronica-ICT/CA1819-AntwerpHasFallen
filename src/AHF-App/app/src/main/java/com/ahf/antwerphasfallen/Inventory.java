package com.ahf.antwerphasfallen;

import java.util.List;

/**
 * Created by Jorren on 16/11/2018.
 */

public class Inventory {
    private int id;
    private List<Ingredient> ingredients;
    private List<ShopItem> shopItems;

    public Inventory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<ShopItem> getShopItems() {
        return shopItems;
    }

    public void setShopItems(List<ShopItem> shopItems) {
        this.shopItems = shopItems;
    }
}
