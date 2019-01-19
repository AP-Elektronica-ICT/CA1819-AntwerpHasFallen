package com.ahf.antwerphasfallen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.ahf.antwerphasfallen.Dialogs.JoinGameDialog;
import com.ahf.antwerphasfallen.Dialogs.JoinTeamDialog;
import com.ahf.antwerphasfallen.Dialogs.SelectTeamsDialog;
import com.ahf.antwerphasfallen.Helpers.GameDataService;
import com.ahf.antwerphasfallen.Helpers.PlayerHandler;
import com.ahf.antwerphasfallen.Helpers.RetrofitInstance;
import com.ahf.antwerphasfallen.Model.Game;
import com.ahf.antwerphasfallen.Model.Player;
import com.ahf.antwerphasfallen.Model.Team;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button btnStart;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private boolean allowLocation = false;

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
                /*Intent intent = new Intent(MainActivity.this, InGameActivity.class);
                startActivity(intent);*/
            }
        });

        Button btnJoin = (Button)findViewById(R.id.btn_join);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoinGameDialog dialog = new JoinGameDialog();
                dialog.show(getSupportFragmentManager(), "Join Games");
            }
        });

        //Check if the app has permission to use location.
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            allowLocation = false;
            //If the app doesn't have permission to use location ask if it can.
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
        else{
            allowLocation = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    allowLocation= true;
                    
                } else {
                    allowLocation = false;
                }
                return;
            }

            /*

                Other permissions the app needs

            */
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
       /*File file = new File(getFilesDir(), PlayerHandler.SAVED_PLAYER);
        file.delete();*/
        Player p = PlayerHandler.getInstance(getApplicationContext()).checkPlayer();
        if(p != null)
            startInGameAcitivity(p);
    }

    @NonNull
    private String[] getTeamNames(Game game) {
        List<Team> teams = game.getTeams();
        String[] teamNames = new String[teams.size()];
        for(int i = 0; i < teams.size(); i++)
            teamNames[i] = teams.get(i).getName();
        return teamNames;
    }



    private void startInGameAcitivity(Player p){
        Intent intent = new Intent(MainActivity.this, InGameActivity.class);
        intent.putExtra("gameId", p.getGameId());
        intent.putExtra("playerId", p.getId());
        intent.putExtra("teamId", p.getTeamId());
        startActivity(intent);
    }

    public void createNewGame(Game game) {
        String[] teamNames = getTeamNames(game);
        Call<Game> call = service.newGame(game.getTeams().size(), teamNames);
        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if(response.body() != null){
                    Game game = response.body();
                    Log.d(TAG, "onResponse: " + game.getId());
                    Bundle data = new Bundle();
                    data.putInt("gameId", game.getId());
                    JoinTeamDialog joinTeamDialog = new JoinTeamDialog();
                    joinTeamDialog.setArguments(data);
                    joinTeamDialog.show(getSupportFragmentManager(), "JoinTeam");
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.toString());
                Toast.makeText(MainActivity.this, "Creating new game failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void joinGame(int gameId, int teamId){
        Call<Player> call = service.joinGame(gameId, teamId);
        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                startInGameAcitivity(response.body());
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.e("onFailure2", t.toString());
            }
        });
    }
}
