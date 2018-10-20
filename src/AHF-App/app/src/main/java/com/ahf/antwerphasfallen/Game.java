package com.ahf.antwerphasfallen;

import java.util.List;

/**
 * Created by Jorren on 20/10/2018.
 */

public class Game {
    private int id;
    private List<Team> teams;

    public Game() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
