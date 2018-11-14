package com.ahf.antwerphasfallen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Jorren on 28/10/2018.
 */

public class JoinGameDialog extends DialogFragment {
    private static final String TAG = "JoinGameDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_joingame, null);

        final EditText txtId = (EditText)dialogView.findViewById(R.id.txt_Id);
        txtId.setHint("Game Id");

        builder.setView(dialogView)
                .setMessage("Fill in the game Id")
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int gameId = Integer.valueOf(txtId.getText().toString()); //TODO: don't allow empty
                        Bundle data = new Bundle();
                        data.putInt("gameId", gameId);
                        JoinTeamDialog joinTeamDialog = new JoinTeamDialog();
                        joinTeamDialog.setArguments(data);
                        joinTeamDialog.show(getFragmentManager(), "JoinTeam");
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
