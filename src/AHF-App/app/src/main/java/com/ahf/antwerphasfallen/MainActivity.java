package com.ahf.antwerphasfallen;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnStart = (Button)findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectTeamsDialog dialog = new SelectTeamsDialog();
                dialog.show(getSupportFragmentManager(), "Select teams ");
            }
        });

        /*LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //makeUseOfNewLocation(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        catch (SecurityException e)
        {
            Log.e("error", e.toString());
        }*/
    }

    @NonNull
    private String[] getTeamNames(Game game) {
        List<Team> teams = game.getTeams();
        String[] teamNames = new String[teams.size()];
        for(int i = 0; i < teams.size(); i++)
            teamNames[i] = teams.get(i).getName();
        return teamNames;
    }

    public void createNewGame(Game game) {
        String[] teamNames = getTeamNames(game);
        Call<Game> call = service.newGame(game.getTeams().size(), teamNames);
        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                Game game = response.body();
                Log.d(TAG, "onResponse: " + game.getId());
                Intent intent = new Intent(MainActivity.this, InGameActivity.class);
                intent.putExtra("gameId", game.getId());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.toString());
            }
        });
    }
}
