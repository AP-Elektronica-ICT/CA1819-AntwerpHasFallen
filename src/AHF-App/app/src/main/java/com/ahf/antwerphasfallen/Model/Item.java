package com.ahf.antwerphasfallen.Model;

/**
 * Created by Jorren on 28/11/2018.
 */

public class Item {
    public static String TYPE_INGREDIENT = "ingredient";
    public static String TYPE_ITEM = "item";

    private int id;
    private String name;
    private String description;
    private String type;

    public Item() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
