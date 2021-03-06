package com.ahf.antwerphasfallen.Model;

import com.ahf.antwerphasfallen.Model.Inventory;
import com.ahf.antwerphasfallen.Model.Player;

import java.util.List;

/**
 * Created by Jorren on 20/10/2018.
 */

public class Team {
    private int id;
    private String name;
    private List<Player> players;
    private Inventory inventory;
    private int money;
    private int timerOffset;
    private String blackout;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }
    
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getMoney() { return money;}

    public String getBlackout() {
        return blackout;
    }

    public int getTimerOffset() {
        return timerOffset;
    }

    public void setTimerOffset(int timerOffset){
        this.timerOffset = timerOffset;
    }
}
