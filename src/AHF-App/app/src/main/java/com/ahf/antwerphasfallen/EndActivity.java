package com.ahf.antwerphasfallen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EndActivity extends AppCompatActivity {

    private int gameId;
    private int teamId;
    private int playerId;

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

        Button bntContinue = findViewById(R.id.btn_continue);
        bntContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainactivity();
            }
        });
    }

    public void startMainactivity(){
        Intent intent = new Intent(EndActivity.this, MainActivity.class);
        startActivity(intent);
    }
}