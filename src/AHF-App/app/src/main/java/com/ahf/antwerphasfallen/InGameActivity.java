package com.ahf.antwerphasfallen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InGameActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Fragment fr;


    public static final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);

    public Player CurrentPlayer;
    public Team CurrentTeam;
    private TextView txtMoney;
    private TextView txtTimer;
    private InventoryFragment inventoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        inventoryFragment = new InventoryFragment();

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
        txtTimer = findViewById(R.id.txt_timer);
        txtMoney = findViewById(R.id.txt_money);
        txtMoney.setText("Game id: " + gameId);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawer = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawer.closeDrawers();

                //update UI
                switch(item.toString()){
                    case "Map":
                        fr = new MapFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("locationId", getRandomLocation());
                        fr.setArguments(bundle);
                        break;
                    case "Inventory":
                            fr =inventoryFragment;
                        break;
                    case "Exit Game":
                        ConfirmEndGameDialog dialog = new ConfirmEndGameDialog();
                        dialog.show(getSupportFragmentManager(), "confirm end game");
                        break;
                }
                if(!item.toString().equals("Exit Game") && !item.toString().equals("Shop") && !item.toString().equals("Team")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, fr);
                    ft.commit();
                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getRandomLocation(){
        Random rand = new Random();
        int id = rand.nextInt(3) + 1;
        return id;
    }
    public void ShowQuiz(int timer){
        txtTimer.setVisibility(View.VISIBLE);
        fr = new QuizFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fr);
        ft.commit();
        new CountDownTimer(timer*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("Time left: " + timeConversion(millisUntilFinished/1000));
            }

            public void onFinish() {
                //code voor als ze nog in de zone zitten
            }
        }.start();
    }
    public void Showsub(int timer){
        txtTimer.setVisibility(View.VISIBLE);
        fr = new SubstitutionFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fr);
        ft.commit();
        new CountDownTimer(timer*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("Time left: " + timeConversion(millisUntilFinished/1000));
            }

            public void onFinish() {
                //code voor als ze nog in de zone zitten
            }
        }.start();
    }

    public void ShowPuzzles(int timer){
        txtTimer.setVisibility(View.VISIBLE);
        fr = new Puzzles();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fr);
        ft.commit();
        new CountDownTimer(timer*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("Time left: " + timeConversion(millisUntilFinished/1000));
            }

            public void onFinish() {
                //code voor als ze nog in de zone zitten
            }
        }.start();
    }

    private static String timeConversion(long totalSeconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        long seconds = totalSeconds % SECONDS_IN_A_MINUTE;
        long totalMinutes = (totalSeconds - seconds) / SECONDS_IN_A_MINUTE;
        long minutes = totalMinutes % MINUTES_IN_AN_HOUR;

        return minutes + ":" + seconds;
    }


    private void loadPlayer(int id) {
        Call<Player> call = service.getPlayer(id);
        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                if (response.body() != null) {
                    CurrentPlayer = response.body();
                    PlayerHandler.getInstance(getApplicationContext()).putPlayerInfo(CurrentPlayer);
                    Call<Team> teamCall = service.getTeam(CurrentPlayer.getTeamId());
                    teamCall.enqueue(new Callback<Team>() {
                        @Override
                        public void onResponse(Call<Team> call, Response<Team> response) {
                            if(response.body() != null) {
                                CurrentTeam = response.body();
                                txtMoney.setText("G:." + CurrentTeam.getMoney());
                                Call<Inventory> inventoryCall = service.getInventory(CurrentTeam.getInventory().getId());
                                inventoryCall.enqueue(new Callback<Inventory>() {
                                    @Override
                                    public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                                        if (response.body() != null) {
                                            CurrentTeam.setInventory(response.body());
                                            inventoryFragment.setAdapters();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Inventory> call, Throwable t) {
                                        Toast.makeText(InGameActivity.this, "Could not retrieve team inventory", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Team> call, Throwable t) {

                        }
                    });
                }
                else startMainActivity();
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {

            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void EndGame(){
        Call<Boolean> call = service.endGame(CurrentPlayer.getGameId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body() != null) {
                    Toast.makeText(InGameActivity.this, "Game ended", Toast.LENGTH_SHORT).show();
                    PlayerHandler.getInstance(getApplicationContext()).deleteFile(PlayerHandler.SAVED_PLAYER);
                    Intent intent = new Intent(InGameActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(InGameActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
