package com.ahf.antwerphasfallen;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.widget.TextView;

public class InGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int gameId = 0;
        int playerId = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            gameId = extras.getInt("gameId");
            playerId = extras.getInt("playerId");
        }

        TextView txtGameId = (TextView)findViewById(R.id.txt_gameId);
        txtGameId.setText("Game id: " + gameId + "\nPlayer id: " + playerId);
    }
}
