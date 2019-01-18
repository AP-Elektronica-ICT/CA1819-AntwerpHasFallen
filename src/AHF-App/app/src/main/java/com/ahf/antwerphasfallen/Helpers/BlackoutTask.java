package com.ahf.antwerphasfallen.Helpers;

import android.os.AsyncTask;

import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.Team;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jorren on 11/01/2019.
 */

public class BlackoutTask extends AsyncTask {
    private static final String TAG = "BlackoutTask";

    private InGameActivity activity;
    private String enemyTeam;

    public BlackoutTask(InGameActivity activity, String enemyTeam){
        this.activity = activity;
        this.enemyTeam = enemyTeam;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.StartBlackout(enemyTeam);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try{
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            Call<Team> teamCall = InGameActivity.service.stopBlackout(activity.CurrentTeam.getId());
            teamCall.enqueue(new Callback<Team>() {
                @Override
                public void onResponse(Call<Team> call, Response<Team> response) {
                    if(response.body() != null)
                        activity.CurrentTeam = response.body();
                }

                @Override
                public void onFailure(Call<Team> call, Throwable t) {

                }
            });
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        activity.StopBlackout();
        Provider.setIsBlackoutRunning(false);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        activity.StopBlackout();
        Provider.setIsBlackoutRunning(false);
    }
}
