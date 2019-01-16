package com.ahf.antwerphasfallen.Helpers;

import android.app.Activity;
import android.support.annotation.UiThread;
import android.util.Log;

import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.Game;
import com.ahf.antwerphasfallen.Model.Player;
import com.ahf.antwerphasfallen.Model.Team;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jorren on 11/01/2019.
 */

public class CheckerThread extends Thread implements Subscriber{
    private static final String TAG = "CheckerThread";

    private InGameActivity activity;
    private Player currentPlayer;
    private int teamId;

    private boolean pause = false;
    private boolean blackoutRunning = false;
    private List<Boolean> statusList;

    public CheckerThread (InGameActivity activity, Player currentPlayer){
        this.activity = activity;
        this.currentPlayer = currentPlayer;
        this.teamId = currentPlayer.getTeamId();
        statusList = new ArrayList<>();
        statusList.add(true);
        Provider.subscribeIsBlackoutRunning(this);
    }

    @Override
    public void run() {
        super.run();
        while (!pause) {
            if (statusList.get(0)) {
                statusList.set(0, false);
                Call<Team> teamCall = InGameActivity.service.getTeam(teamId);
                teamCall.enqueue(new Callback<Team>() {
                    @Override
                    public void onResponse(Call<Team> call, Response<Team> response) {
                        if (response.body() != null) {
                            Team team = response.body();
                            if (team.getBlackout() != null) {
                                runBlackout(team.getBlackout());
                            }
                            statusList.set(0, true);
                        }
                        else if (response.code() == 404){
                            checkGameStillExists();
                        }
                    }

                    @Override
                    public void onFailure(Call<Team> call, Throwable t) {
                        Log.e(TAG, "onFailure: Failed to get team");
                        statusList.set(0, true);
                    }
                });
                while(statusList.get(0)){}
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Stop(){
        pause = true;
    }

    private void runBlackout(final String enemyTeam){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!blackoutRunning) {
                    BlackoutTask blackout = new BlackoutTask(activity, enemyTeam);
                    blackout.execute();
                    Provider.setIsBlackoutRunning(true);
                }
            }
        });
    }

    private void checkGameStillExists(){
        Call<List<Game>> gamesCall = InGameActivity.service.getGames();
        gamesCall.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if(response.body() != null){
                    List<Game> games = response.body();
                    boolean found = false;
                    for(Game g : games){
                        if(g.getId() == currentPlayer.getGameId()){
                            found = true;
                        }
                    }
                    if(!found){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PlayerHandler.getInstance(activity).deleteFile(PlayerHandler.SAVED_PLAYER);
                            }
                        });
                    }
                }
                statusList.set(0, true);
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e(TAG, "onFailure: connection with api failed", t);
                statusList.set(0, true);
            }
        });
    }

    @Override
    public void Update() {
        blackoutRunning = Provider.isBlackoutRunning();
    }
}
