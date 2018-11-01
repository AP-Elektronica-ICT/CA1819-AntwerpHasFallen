package com.ahf.antwerphasfallen;

/**
 * Created by Jorren on 31/10/2018.
 */

public class Player {

    private int Id;
    private int GameId;
    private int TeamId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getGameId() {
        return GameId;
    }

    public void setGameId(int gameId) {
        GameId = gameId;
    }

    public int getTeamId() {
        return TeamId;
    }

    public void setTeamId(int teamId) {
        TeamId = teamId;
    }
}
