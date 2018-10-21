package com.ahf.antwerphasfallen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorren on 21/10/2018.
 */

public class CreateNewGameDialog extends DialogFragment {
    private static final String TAG = "CreateNewGameDialog";

    public static final String PREFERENCES_NAME = "teamnames";
    private SharedPreferences preferences;
    private MainActivity host;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_creategame, null);

        preferences = getContext().getSharedPreferences(CreateNewGameDialog.PREFERENCES_NAME, getContext().MODE_PRIVATE);

        final NumberPicker npTeams = (NumberPicker)dialogView.findViewById(R.id.np_teams);
        ListView listTeamNames = (ListView)dialogView.findViewById(R.id.list_teamnames);
        final Game game = new Game();
        final ArrayList<Team> teams = new ArrayList<>();
        for(int i = 0; i < 2; i++)
            teams.add(new Team());
        game.setTeams(teams);
        final TeamNamesListAdapter adapter = new TeamNamesListAdapter(getActivity(), teams);
        listTeamNames.setAdapter(adapter);
        npTeams.setMinValue(2);
        npTeams.setMaxValue(10);
        npTeams.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                teams.clear();
                for( int j = 0; j < i1; j++)
                    teams.add(new Team());
                adapter.notifyDataSetChanged();
                game.setTeams(teams);
            }
        });


        builder.setView(dialogView)
                .setTitle("choose teamnames")
                .setMessage("choose amount of teams")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), game.getTeams().get(0).getName(), Toast.LENGTH_LONG).show();
                        host.createNewGame(game);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            host = (MainActivity) context;
        }catch (ClassCastException e){
            Log.d(TAG, "onAttach: " + context.toString() + " is not mainactivity");
        }
    }
}
