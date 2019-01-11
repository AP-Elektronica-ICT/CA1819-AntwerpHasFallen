package com.ahf.antwerphasfallen.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ahf.antwerphasfallen.Model.Game;
import com.ahf.antwerphasfallen.Helpers.GameDataService;
import com.ahf.antwerphasfallen.MainActivity;
import com.ahf.antwerphasfallen.R;
import com.ahf.antwerphasfallen.Helpers.RetrofitInstance;
import com.ahf.antwerphasfallen.Model.Team;
import com.ahf.antwerphasfallen.Adapters.TeamNamesRBListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jorren on 28/10/2018.
 */

public class JoinTeamDialog extends DialogFragment {
    private static final String TAG = "JoinTeamDialog";

    final protected Game game = new Game();
    protected TeamNamesRBListAdapter adapter;

    private String title;
    private String positiveBtnText;
    private String negativeBtnText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_jointeam, null);

        //final ArrayList<Team> teams = new ArrayList<>();
        adapter = new TeamNamesRBListAdapter(getContext(), (ArrayList<Team>)game.getTeams());
        ListView rbListTeamnames = (ListView)dialogView.findViewById(R.id.rbList_teamnames);
        final TeamNamesRBListAdapter adapter = new TeamNamesRBListAdapter(getContext(), (ArrayList<Team>)game.getTeams());
        rbListTeamnames.setAdapter(adapter);

        int gameId = 0;
        if(getArguments() != null) {
            if (getArguments().getInt("gameId") != 0)
                gameId = getArguments().getInt("gameId");
            if(getArguments().getString("title") != null)
                title = getArguments().getString("title");
            else
                title = "Choose your team";
            if(getArguments().getString("positiveButton") != null)
                positiveBtnText = getArguments().getString("positiveButton");
            else
                positiveBtnText = "Join";
            if(getArguments().getString("negativeButton") != null)
                negativeBtnText = getArguments().getString("negativeButton");
            else
                negativeBtnText = "Cancel";
        }
        final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
        if(gameId != 0){
            Call<Game> call = service.getGame(gameId);
            call.enqueue(new Callback<Game>() {
                @Override
                public void onResponse(Call<Game> call, Response<Game> response) {
                    Game joinGame = response.body();
                    if(joinGame != null){
                        //game.setTeams(joinGame.getTeams());
                        game.setId(joinGame.getId());
                        game.getTeams().clear();
                        for(Team t : joinGame.getTeams())
                            game.getTeams().add(t);
                        adapter.notifyDataSetChanged();
                    }
                    else
                        displayErrorAndCancel();
                }

                @Override
                public void onFailure(Call<Game> call, Throwable t) {
                    t.printStackTrace();
                    displayErrorAndCancel();
                }
            });
        }
        else
            displayErrorAndCancel();

        builder.setView(dialogView)
                .setTitle(title)
                .setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        positiveButtonClick(dialogInterface, i);
                    }
                })
                .setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        return builder.create();
    }

    private void displayErrorAndCancel(){
        Toast.makeText(getContext(), "something went wrong, please try again", Toast.LENGTH_LONG).show();
        getDialog().cancel();
    }

    protected void positiveButtonClick(DialogInterface dialogInterface, int i){
        Team joinTeam = adapter.getItem(adapter.getSelectedPosition());
        Toast.makeText(getContext(), "joining game: " + game.getId() + " and team: " + joinTeam.getName(), Toast.LENGTH_SHORT).show();
        try{
            MainActivity host = (MainActivity)getActivity();
            host.joinGame(game.getId(), joinTeam.getId());
        }catch (ClassCastException e){
            Log.d(TAG, getActivity().toString() + " is not mainactivity");
            e.printStackTrace();
            Toast.makeText(getContext(), "joining game " + game.getId() + " failed", Toast.LENGTH_LONG).show();
        }
    }


}
