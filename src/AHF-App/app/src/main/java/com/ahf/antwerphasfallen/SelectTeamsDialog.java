package com.ahf.antwerphasfallen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.NumberPicker;

/**
 * Created by Jorren on 26/10/2018.
 */

public class SelectTeamsDialog extends DialogFragment {
    private static final String TAG = "SelectTeamsDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select_teams, null);

        final NumberPicker numberPicker = (NumberPicker)dialogView.findViewById(R.id.np_teams);
        numberPicker.setMinValue(2);
        numberPicker.setMaxValue(10);

        builder.setView(dialogView)
                .setTitle("Teams")
                .setMessage("Choose the amount of teams")
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int teams = numberPicker.getValue();
                        Bundle data = new Bundle();
                        data.putInt("teams", teams);
                        CreateTeamsDialog teamsDialog = new CreateTeamsDialog();
                        teamsDialog.setArguments(data);
                        teamsDialog.show(getFragmentManager(), "TeamCreateDialog");
                        //teamsDialog.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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
}
