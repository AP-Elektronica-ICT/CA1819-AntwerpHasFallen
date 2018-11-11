package com.ahf.antwerphasfallen;

import java.util.List;

/**
 * Created by Jorren on 20/10/2018.
 */

public class Team {
    private int id;
    private String name;
    private List<Player> players;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setPlayers(List<Player> playerCount) {
        this.players = playerCount;
    }
}
