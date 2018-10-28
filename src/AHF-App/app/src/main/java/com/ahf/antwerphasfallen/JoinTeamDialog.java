package com.ahf.antwerphasfallen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jorren on 28/10/2018.
 */

public class JoinTeamDialog extends DialogFragment {
    private static final String TAG = "JoinTeamDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_jointeam, null);

        final Game game = new Game();
        //final ArrayList<Team> teams = new ArrayList<>();
        ListView rbListTeamnames = (ListView)dialogView.findViewById(R.id.rbList_teamnames);
        final TeamNamesRBListAdapter adapter = new TeamNamesRBListAdapter(getContext(), (ArrayList<Team>)game.getTeams());
        rbListTeamnames.setAdapter(adapter);

        int gameId = 0;
        if(getArguments() != null)
            if(getArguments().getInt("gameId") != 0)
                gameId = getArguments().getInt("gameId");
        GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
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
                .setTitle("Choose your team")
                .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "joining game: " + game.getId() + " and team: " + adapter.getItem(adapter.getSelectedPosition()).getName(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
}
