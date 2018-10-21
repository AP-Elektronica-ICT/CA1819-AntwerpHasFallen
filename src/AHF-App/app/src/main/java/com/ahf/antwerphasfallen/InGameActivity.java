package com.ahf.antwerphasfallen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        int id = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            id = extras.getInt("gameId");

        TextView txtGameId = (TextView)findViewById(R.id.txt_gameId);
        txtGameId.setText("Game id: " + id);
    }
}
