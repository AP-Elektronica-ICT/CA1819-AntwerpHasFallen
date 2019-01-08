package com.ahf.antwerphasfallen.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.ahf.antwerphasfallen.InGameActivity;

/**
 * Created by Jorren on 14/11/2018.
 */

public class ConfirmEndGameDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Are you sure?")
                .setMessage("Are you sure you want to end the game?\nIf you end the game all your progress and the progress of all the teams will be deleted")
                .setPositiveButton("End Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            InGameActivity host = (InGameActivity)getActivity();
                            host.EndGame();
                        }catch (ClassCastException e){
                            e.printStackTrace();
                        }
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
