package com.ahf.antwerphasfallen.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.Team;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jorren on 9/01/2019.
 */

public class BackgroundChecker {
    private static final String TAG = "BackgroundChecker";

    private AsyncTask check;
    private static BackgroundChecker instance;
    final List<Boolean> statusList = new ArrayList<>();

    private InGameActivity activity;

    public BackgroundChecker(InGameActivity hostActivity) {
        this.activity = hostActivity;
        statusList.add(true); //check if call has been answered
        statusList.add(false); //check if task is already running
    }

    public static BackgroundChecker getInstance(InGameActivity activity) {
        if (instance == null)
            instance = new BackgroundChecker(activity);
        return instance;
    }

    public void StartChecking(final int teamId) {
        if (!statusList.get(1) && statusList.get(0)) {
            statusList.set(1, true);
            check = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    while (!isCancelled()) {
                        if (statusList.get(0)) {
                            statusList.set(0, false);
                            Call<Team> teamCall = InGameActivity.service.getTeam(teamId);
                            teamCall.enqueue(new Callback<Team>() {
                                @Override
                                public void onResponse(Call<Team> call, Response<Team> response) {
                                    if (response.body() != null) {
                                        Team team = response.body();
                                        if (team.getBlackout() != null) {
                                            onProgressUpdate(new Object[]{"start", team.getBlackout()});
                                            //activity.StartBlackout(team.getBlackout());
                                            try {
                                                Thread.sleep(10000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            } finally {
                                                onProgressUpdate(new Object[]{"stop"});
                                                //activity.StopBlackout();
                                            }
                                        }
                                    }
                                    statusList.set(0, true);
                                }

                                @Override
                                public void onFailure(Call<Team> call, Throwable t) {
                                    Log.e(TAG, "onFailure: Failed to get team");
                                    statusList.set(0, true);
                                }
                            });
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(Object[] values) {
                    super.onProgressUpdate(values);
                    if(values[0].toString().equals("start"))
                        activity.StartBlackout(values[1].toString());
                    else if(values[0].toString().equals("stop"))
                        activity.StopBlackout();
                }
            }.execute();
        }
    }

    public void StopChecking() {
        if(check != null)
            check.cancel(true);
        statusList.set(1, false);
    }
}
