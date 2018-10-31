package com.ahf.antwerphasfallen;

/**
 * Created by Jorren on 31/10/2018.
 */

public class Player {

    private int Id;
    private Game Game;
    private int TeamId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Game getGame() {
        return Game;
    }

    public void setGame(Game game) {
        Game = game;
    }

    public int getTeamId() {
        return TeamId;
    }

    public void setTeamId(int teamId) {
        TeamId = teamId;
    }
}
