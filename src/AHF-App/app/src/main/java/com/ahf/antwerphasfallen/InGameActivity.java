package com.ahf.antwerphasfallen;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahf.antwerphasfallen.Dialogs.ConfirmEndGameDialog;
import com.ahf.antwerphasfallen.Fragments.AnagramFragment;
import com.ahf.antwerphasfallen.Fragments.DadFragment;
import com.ahf.antwerphasfallen.Fragments.InfoFragment;
import com.ahf.antwerphasfallen.Fragments.InventoryFragment;
import com.ahf.antwerphasfallen.Fragments.MapFragment;
import com.ahf.antwerphasfallen.Fragments.QuizFragment;
import com.ahf.antwerphasfallen.Fragments.ShopFragment;
import com.ahf.antwerphasfallen.Fragments.SubstitutionFragment;
import com.ahf.antwerphasfallen.Fragments.TeamFragment;
import com.ahf.antwerphasfallen.Helpers.CheckerThread;
import com.ahf.antwerphasfallen.Helpers.GameDataService;
import com.ahf.antwerphasfallen.Helpers.PlayerHandler;
import com.ahf.antwerphasfallen.Helpers.RetrofitInstance;
import com.ahf.antwerphasfallen.Model.Inventory;
import com.ahf.antwerphasfallen.Model.InventoryItem;
import com.ahf.antwerphasfallen.Model.Item;
import com.ahf.antwerphasfallen.Model.Location;
import com.ahf.antwerphasfallen.Model.Player;
import com.ahf.antwerphasfallen.Model.Puzzles;
import com.ahf.antwerphasfallen.Model.Team;

import java.util.ArrayList;

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
    private TextView txtBlackout;
    private LinearLayout content;
    private RelativeLayout blackout;
    private TextView txtTitle;

    public InventoryFragment inventoryFragment;
    public ShopFragment shopFragment;
    private MenuItem mapItem;
    private Fragment puzzleFragment;
    private int teamId;
    private String locationName;
    private int locationTime;
    private double locationLat;
    private double locationLon;
    private Bundle bundle;
    private int gameId;
    private int playerId;
    private AlertDialog.Builder alertBuilder;
    private AlertDialog.Builder noMoreLocationsAlert;
    private boolean gotIngredient = false;
    private boolean foundMissingIngredient = false;
    private boolean canStartTimer = true;
    private boolean canGetLocation = true;
    private boolean newLocation = true;
    public boolean openquiz;
    public boolean opensub;
    public boolean openanagram;
    public boolean opendad;

    private ArrayList<String> missingIngredients;

    private CheckerThread backgroundChecker = null;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        inventoryFragment = new InventoryFragment();
        shopFragment = new ShopFragment();
        missingIngredients = new ArrayList<String>();

        fr = new InfoFragment();

        txtTimer = findViewById(R.id.txt_timer);
        bundle = new Bundle();


        mDrawer = findViewById(R.id.drawer_layout);
        txtMoney = findViewById(R.id.txt_money);
        txtBlackout = findViewById(R.id.txt_blackout);
        content = findViewById(R.id.content_linear);
        blackout = findViewById(R.id.layout_blackout);
        txtTitle = findViewById(R.id.txt_title);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
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
                txtTitle.setText(item.toString());
                //update UI
                switch (item.toString()) {
                    case "Team":
                        fr = new TeamFragment();
                        bundle = new Bundle();
                        bundle.putString("teamName", CurrentTeam.getName());
                        bundle.putInt("gameId", gameId);
                        bundle.putStringArrayList("ingredients" , missingIngredients);
                        fr.setArguments(bundle);
                        break;
                    case "Map":
                        mapItem = item;
                        fr = new MapFragment();
                        bundle = new Bundle();
                        GetRandomLocation();
                        break;
                    case "Inventory":
                        fr = inventoryFragment;
                        break;
                    case "Shop":
                        fr = shopFragment;
                        break;
                    case "Exit Game":
                        ConfirmEndGameDialog dialog = new ConfirmEndGameDialog();
                        dialog.show(getSupportFragmentManager(), "confirm end game");
                        break;
                    case "Puzzle":
                        fr = puzzleFragment;
                        break;
                }
                if (!item.toString().equals("Exit Game")&& !item.toString().equals("Map")) {
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

    public void GetRandomLocation(){
        if(canGetLocation){
            gotIngredient = false;
            Call<Location> randomLocationCall = service.getRandomLocation(teamId);
            randomLocationCall.enqueue(new Callback<Location>() {
                @Override
                public void onResponse(Call<Location> call, Response<Location> response) {
                    //check if there are still locations the team hasn't visited yet
                    if(response.body() != null){
                        canGetLocation = false;
                        newLocation = true;
                        locationTime = response.body().getTime();
                        locationName = response.body().getName();
                        locationLat = response.body().getLat();
                        locationLon = response.body().getLon();
                        bundle.putString("locationTitle", locationName);
                        bundle.putDouble("lat", locationLat);
                        bundle.putDouble("lon", locationLon);
                        bundle.putInt("locationTime", locationTime);
                        bundle.putBoolean("newLocation", newLocation);
                        fr.setArguments(bundle);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, fr);
                        ft.commit();
                    }
                    else{
                        ShowDialog("Alert!", "You visited all the locations but didn't find the ingredients for the cure, your only hope now is that the other team does.");
                    }

                }

                @Override
                public void onFailure(Call<Location> call, Throwable t) {
                    //locationId = -1;
                }
            });
        }
        else{
            newLocation = false;
            bundle.putString("locationTitle", locationName);
            bundle.putDouble("lat", locationLat);
            bundle.putDouble("lon", locationLon);
            bundle.putInt("locationTime", locationTime);
            bundle.putBoolean("newLocation", newLocation);
            fr.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fr);
            ft.commit();
        }
    }

    public void UpdateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtMoney.setText("G: " + CurrentTeam.getMoney());
                shopFragment.LoadItems();
                inventoryFragment.UpdateInventory();
            }
        });
    }

    public void ShowQuiz() {
        fr = new QuizFragment();
        txtTimer.setVisibility(View.VISIBLE);
        bundle.putString("target",locationName);
        fr.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fr);
        ft.commit();
        openquiz = true;
    }

    public void ShowDad() {
        fr = new DadFragment();
        txtTimer.setVisibility(View.VISIBLE);
        bundle.putString("target",locationName);
        fr.setArguments(bundle);
        opendad = true;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,fr);
        ft.commit();
    }

    public void ShowAnagram(boolean check) {
        fr = new AnagramFragment();
        txtTimer.setVisibility(View.VISIBLE);
        bundle.putString("target",locationName);
        fr.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,fr);
        ft.commit();
        openanagram = true;


    }

    public void Showsub() {
        fr = new SubstitutionFragment();
        txtTimer.setVisibility(View.VISIBLE);
        bundle.putString("target",locationName);
        fr.setArguments(bundle);



        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fr);
        ft.commit();
        opensub = true;
    }

    public void ShowPuzzles(boolean status) {
        txtTitle.setText("Puzzles");
        loadTeam(CurrentPlayer.getTeamId());

        txtTimer.setVisibility(View.VISIBLE);
        fr = new Puzzles();

        if (mapItem != null) {
            mapItem.setTitle("Puzzle");
        }

        if (status)
        {
            openquiz = false;
            opensub = false;
            openanagram= false;
            opendad = false;
        }
        puzzleFragment = fr;
        bundle.putBoolean("Stat",status);
        bundle.putBoolean("Quiz",openquiz);
        bundle.putBoolean("Sub",opensub);
        bundle.putBoolean("Anagram",openanagram);
        bundle.putBoolean("Dad", opendad);
        fr.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fr);
        ft.commit();
        if(canStartTimer)
        {
            canStartTimer = false;
            Timer(locationTime);
        }
    }

    private static String timeConversion(long totalSeconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;
        String sec;

        long seconds = totalSeconds % SECONDS_IN_A_MINUTE;
        long totalMinutes = (totalSeconds - seconds) / SECONDS_IN_A_MINUTE;
        long minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        if (seconds < 10) {
            sec = "0" + seconds;
        } else {
            sec = String.valueOf(seconds);
        }

        return minutes + ":" + sec;
    }

    public void ToLongInZone(){
        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Alert")
                .setMessage("Your time is up and you stayed to long! Your team lost G20")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LeavePuzzles();
                    }
                });
        alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                LeavePuzzles();
            }
        });
        alertBuilder.show();
    }

    public void LeavePuzzles(){
        timer.cancel();
        txtTimer.setVisibility(View.GONE);
        canGetLocation = true;
        canStartTimer = true;
        if (mapItem != null)
            mapItem.setTitle("Map");

        fr = new MapFragment();
        bundle = new Bundle();
        GetRandomLocation();
    }

    public void Timer(int seconds){
        timer = new CountDownTimer(seconds * 1000, 1000) {

            boolean finished;
            private long timeLeft;

            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished / 1000 + CurrentTeam.getTimerOffset();
                if(timeLeft > 0) {
                    txtTimer.setText("Time left: " + timeConversion(timeLeft));
                    finished = false;
                }
                else
                    onFinish();
            }

            public void onFinish() {
                //code voor als ze nog in de zone zitten
                if(timeLeft > 0){
                    timer.cancel();
                    Timer((int)timeLeft);
                    CurrentTeam.setTimerOffset(0);
                    Call<Team> resetCall = service.resetTimer(CurrentTeam.getId());
                    resetCall.enqueue(new Callback<Team>() {
                        @Override
                        public void onResponse(Call<Team> call, Response<Team> response) {
                            if(response.body() != null){
                                CurrentTeam = response.body();
                            }
                        }

                        @Override
                        public void onFailure(Call<Team> call, Throwable t) {

                        }
                    });
                }
                else{
                    if(!finished){
                        finished = true;
                        UpdateMoney(-20);
                        ToLongInZone();
                    }
                }
            }
        }.start();
    }

    public void UpdateMoney(int amount){
        Call<Team> updateMoneyCall = service.updateMoney(CurrentTeam.getId(), amount);
        updateMoneyCall.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                if(response.body() != null)
                {
                    CurrentTeam = response.body();
                    UpdateUI();
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {

            }
        });
    }

    public void ReceiveReward(boolean answer, String difficulty){
        Call<Team> getReward = service.reward(CurrentTeam.getId(), difficulty, Boolean.toString(answer), Boolean.toString(gotIngredient) ,missingIngredients);
        getReward.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                if(response.body() != null){
                    int oldInventoryCount = CurrentTeam.getInventory().getIngredients().size();
                    int oldMoney = CurrentTeam.getMoney();
                    CurrentTeam = response.body();
                    int receivedMoney = CurrentTeam.getMoney() - oldMoney;
                    //Log.e("received", "old: " + oldMoney + ", new: " + CurrentTeam.getMoney() + ", received: " + receivedMoney);
                    if (CurrentTeam.getInventory().getIngredients().size() > oldInventoryCount){
                        gotIngredient = true;
                        if(receivedMoney > 0)
                            ShowDialog("Reward received!", "You received an ingredient and " + receivedMoney + "G! Check your inventory to see your ingredients.");
                        else
                            ShowDialog("Reward received!", "You received an ingredient! Check your inventory to see your ingredients.");
                    }
                    else
                        if(receivedMoney > 0)
                            ShowDialog("Reward received!", "You received " + receivedMoney + "G.");
                        else
                            ShowDialog("Money lost...", "You lost " + receivedMoney + "G.");
                    UpdateUI();
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {

            }
        });
    }

    public void ShowDialog(String title, String message){
        alertBuilder = new AlertDialog.Builder(InGameActivity.this);
        alertBuilder.setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CheckIngredients();
                    }
                });
        alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                CheckIngredients();
            }
        });
        alertBuilder.show();
    }

    public void CheckIngredients() {
        missingIngredients.clear();
        Log.e("Ingredients", "Number of ingredients: " + CurrentTeam.getInventory().getIngredients().size());
        if(CurrentTeam.getInventory().getIngredients().size() < 3){
            Call<ArrayList<Item>> ingredientCall = service.getIngredients();
            ingredientCall.enqueue(new Callback<ArrayList<Item>>() {
                @Override
                public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                    try {
                        if(CurrentTeam.getInventory().getIngredients().size() == 0){
                            for (Item ingredient : response.body()){
                                missingIngredients.add(ingredient.getName());
                            }
                        }
                        else{
                            for (Item ingredient : response.body()) {
                                for (InventoryItem inventoryItem : CurrentTeam.getInventory().getIngredients()){
                                    if(inventoryItem.getItem().getId() != ingredient.getId()){
                                        foundMissingIngredient = true;
                                    }
                                    else
                                    {
                                        foundMissingIngredient = false;
                                        break;
                                    }
                                }
                                if(foundMissingIngredient){
                                    missingIngredients.add(ingredient.getName());
                                    foundMissingIngredient = false;
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("error", e.toString());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Item>> call, Throwable t) {

                }
            });
        }
        else {
            GameWon();
        }
    }

    public void GameWon(){
        alertBuilder = new AlertDialog.Builder(InGameActivity.this);
        alertBuilder.setTitle("You won!")
                .setMessage("Congratulations your team has found all the ingredients to make the cure! You won the game!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //endgame
                    }
                });
        alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                //endgame
            }
        });
        alertBuilder.show();
    }

    public void StartBlackout(String enemyTeam){
        txtBlackout.setText("Your team has been sent a blackout by " + enemyTeam);
        blackout.setVisibility(View.VISIBLE);
        content.setVisibility(View.INVISIBLE);
    }

    public void StopBlackout(){
        content.setVisibility(View.VISIBLE);
        blackout.setVisibility(View.INVISIBLE);
    }

    public void loadTeam(int teamId){
        Call<Team> teamCall = service.getTeam(teamId);
        teamCall.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                if(response.body() != null){
                    CurrentTeam = response.body();
                    UpdateUI();
                }
                else
                    Toast.makeText(InGameActivity.this, "Failed getting team information", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                Toast.makeText(InGameActivity.this, "Failed getting team information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadPlayer(int id) {
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
                            if (response.body() != null) {
                                CurrentTeam = response.body();
                                backgroundChecker = new CheckerThread(InGameActivity.this, CurrentPlayer);
                                backgroundChecker.start();
                                teamId = CurrentTeam.getId();
                                txtMoney.setText("G:." + CurrentTeam.getMoney());
                                bundle.putString("teamName", CurrentTeam.getName());
                                bundle.putInt("gameId", gameId);
                                fr.setArguments(bundle);
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.fragment_container, fr);
                                ft.commitAllowingStateLoss();
                                Call<Inventory> inventoryCall = service.getInventory(CurrentTeam.getInventory().getId());
                                inventoryCall.enqueue(new Callback<Inventory>() {
                                    @Override
                                    public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                                        if (response.body() != null) {
                                            CurrentTeam.setInventory(response.body());
                                            UpdateUI();
                                            CheckIngredients();
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
                            Toast.makeText(InGameActivity.this, "Error getting team information", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else startMainActivity(true);
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Toast.makeText(InGameActivity.this, "Error getting player information", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startMainActivity(boolean delete) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("delete", delete);
        startActivity(intent);
    }

    public void EndGame() {
        Call<Boolean> call = service.endGame(CurrentPlayer.getGameId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body() != null) {
                    Toast.makeText(InGameActivity.this, "Game ended", Toast.LENGTH_SHORT).show();
                    ShowEndScreen();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(InGameActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ShowEndScreen(){
        PlayerHandler.getInstance(getApplicationContext()).deleteFile(PlayerHandler.SAVED_PLAYER);
        Intent intent = new Intent(InGameActivity.this, EndActivity.class);
        intent.putExtra("gameId", CurrentPlayer.getGameId());
        intent.putExtra("playerId", CurrentPlayer.getId());
        intent.putExtra("teamId", CurrentPlayer.getTeamId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(CurrentTeam != null && backgroundChecker != null){
            backgroundChecker = new CheckerThread(InGameActivity.this, CurrentPlayer);
            backgroundChecker.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(backgroundChecker != null)
            backgroundChecker.Stop();
    }

    @Override
    public void onBackPressed(){

    }
}
