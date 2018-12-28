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

import java.util.List;
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
    private MenuItem mapItem;
    private Fragment puzzleFragment;
    private int teamId;
    private int locationId;
    private boolean checking;
    private List<Location> previousLocations;
    private Bundle bundle;
    private int gameId;
    private int playerId;
    private int[] pastLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        inventoryFragment = new InventoryFragment();
        txtTimer = findViewById(R.id.txt_timer);
        fr = new TeamFragment();
        bundle = new Bundle();
        mDrawer = findViewById(R.id.drawer_layout);
        txtMoney = findViewById(R.id.txt_money);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            gameId = extras.getInt("gameId");
            playerId = extras.getInt("playerId");
            loadPlayer(playerId);
        }

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawer.closeDrawers();

                //update UI
                switch(item.toString()){
                    case "Team":
                        fr = new TeamFragment();
                        bundle = new Bundle();
                        bundle.putString("teamName", CurrentTeam.getName());
                        bundle.putInt("gameId", gameId);
                        fr.setArguments(bundle);
                        break;
                    case "Map":
                        mapItem = item;
                        fr = new MapFragment();
                        bundle = new Bundle();
                        bundle.putInt("locationId", getRandomLocation());
                        fr.setArguments(bundle);
                        break;
                    case "Inventory":
                        fr = inventoryFragment;
                        break;
                    case "Exit Game":
                        ConfirmEndGameDialog dialog = new ConfirmEndGameDialog();
                        dialog.show(getSupportFragmentManager(), "confirm end game");
                        break;
                    case "Puzzle":
                        fr = puzzleFragment;
                        break;
                }
                if(!item.toString().equals("Exit Game") && !item.toString().equals("Shop")) {
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
        checking = true;

        while(checking){
            Call<Location> randomLocationCall = service.getRandomLocation(teamId);
            randomLocationCall.enqueue(new Callback<Location>() {
                @Override
                public void onResponse(Call<Location> call, Response<Location> response) {
                    locationId = response.body().getId();
                    if(previousLocations.contains(response.body())){
                        checking = true;
                    }
                    else{
                        previousLocations.add(response.body());
                        checking = false;
                    }
                }

                @Override
                public void onFailure(Call<Location> call, Throwable t) {
                    locationId = -1;
                }
            });
        }

        return locationId;
    }
    public void ShowQuiz(){
        txtTimer.setVisibility(View.VISIBLE);
        fr = new QuizFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fr);
        ft.commit();
    }
    public void Showsub(){
        txtTimer.setVisibility(View.VISIBLE);
        fr = new SubstitutionFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fr);
        ft.commit();
    }

    public void ShowPuzzles(int timer){
        txtTimer.setVisibility(View.VISIBLE);
        fr = new Puzzles();
        if(mapItem != null){
            mapItem.setTitle("Puzzle");
        }
        puzzleFragment = fr;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fr);
        ft.commit();
        new CountDownTimer(timer*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("Time left: " + timeConversion(millisUntilFinished/1000));
            }

            public void onFinish() {
                //code voor als ze nog in de zone zitten
                if(mapItem != null){
                    mapItem.setTitle("Map");
                }
            }
        }.start();
    }

    private static String timeConversion(long totalSeconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;
        String sec;

        long seconds = totalSeconds % SECONDS_IN_A_MINUTE;
        long totalMinutes = (totalSeconds - seconds) / SECONDS_IN_A_MINUTE;
        long minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        if (seconds < 10){
            sec = "0" + seconds;
        }else{
            sec = String.valueOf(seconds);
        }

        return minutes + ":" + sec;
    }

    private void loadLocations(){
        Call<LocationList> call = service.getLocations();
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
                                teamId = CurrentTeam.getId();
                                txtMoney.setText("G:." + CurrentTeam.getMoney());
                                bundle.putString("teamName", CurrentTeam.getName());
                                bundle.putInt("gameId", gameId);
                                fr.setArguments(bundle);
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.fragment_container, fr);
                                ft.commit();
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
