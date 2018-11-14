package com.ahf.antwerphasfallen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InGameActivity extends AppCompatActivity {


    public static final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);

    public Player CurrentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        int gameId = 0;
        int playerId = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            gameId = extras.getInt("gameId");
            playerId = extras.getInt("playerId");
            loadPlayer(playerId);
        }

        TextView txtGameId = (TextView)findViewById(R.id.txt_gameId);
        txtGameId.setText("Game id: " + gameId + "\nPlayer id: " + playerId);

        Button btn_endGame = findViewById(R.id.btn_endGame);
        btn_endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void loadPlayer(int id) {
        Call<Player> call = service.getPlayer(id);
        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                CurrentPlayer = response.body();
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {

            }
        });
    }

    public void EndGame(){
        Call<Boolean> call = service.endGame(CurrentPlayer.getGameId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }
}
