package com.ahf.antwerphasfallen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahf.antwerphasfallen.Model.FinishedGame;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndActivity extends AppCompatActivity {

    private int gameId;
    private int teamId;
    private int playerId;

    private TextView txtEndInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameId = extras.getInt("gameId");
            teamId = extras.getInt("teamId");
            playerId = extras.getInt("playerId");
        }

        if(gameId != 0){
            Call<FinishedGame> finishedGameCall = InGameActivity.service.getFinishedGame(gameId);
            finishedGameCall.enqueue(new Callback<FinishedGame>() {
                @Override
                public void onResponse(Call<FinishedGame> call, Response<FinishedGame> response) {
                    if(response.body() != null)
                        UpdateUI(response.body());
                    else
                        Toast.makeText(EndActivity.this, "Failed to get scores info", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<FinishedGame> call, Throwable t) {
                    Toast.makeText(EndActivity.this, "Failed to get scores info", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Button bntContinue = findViewById(R.id.btn_continue);
        bntContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainactivity();
            }
        });

        txtEndInfo = findViewById(R.id.txt_end);
    }

    private void UpdateUI(FinishedGame game) {
        String teams[] = game.getTeamsLeaderboard().split("/");
        for (String team : teams) {
            team = team.replace(":", ":\t");
        }
        String endText = "The game has been won by " + game.getWinner() != null? game.getWinner() : "more than one team" + "!\n\nLeaderboard: \n";
        for(int i=0; i<teams.length; i++){
            endText += (i + 1) + ".\t" + teams[i] + "\n";
        }
        endText += "We hope you enjoyed the game!\n\nPress the continue button to return back to the main screen";
        txtEndInfo.setText(endText);
    }

    public void startMainactivity(){
        Intent intent = new Intent(EndActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
