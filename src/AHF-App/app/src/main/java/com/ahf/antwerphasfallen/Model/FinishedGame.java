package com.ahf.antwerphasfallen.Model;

/**
 * Created by Jorren on 17/01/2019.
 */

public class FinishedGame {
    private int id;
    private int gameId;
    private String TeamsLeaderboard;
    private String winner;

    public int getGameId() {
        return gameId;
    }

    public String getTeamsLeaderboard() {
        return TeamsLeaderboard;
    }

    public String getWinner() {
        return winner;
    }
}
