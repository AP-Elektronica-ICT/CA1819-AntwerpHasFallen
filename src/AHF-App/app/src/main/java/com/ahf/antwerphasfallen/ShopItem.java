package com.ahf.antwerphasfallen;

/**
 * Created by Jorren on 16/11/2018.
 */

public class ShopItem extends Item {

    private String description;

    public ShopItem() {
    }

    public void Use(){
        //TODO: api call for use method
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
