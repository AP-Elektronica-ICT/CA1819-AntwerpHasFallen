package com.ahf.antwerphasfallen.Model;

/**
 * Created by Jorren on 17/01/2019.
 */

public class FinishedGame {
    private int id;
    private int gameId;
    private String teamsLeaderboard;
    private String winner;

    public int getGameId() {
        return gameId;
    }

    public String getTeamsLeaderboard() {
        return teamsLeaderboard;
    }

    public String getWinner() {
        return winner;
    }
}
