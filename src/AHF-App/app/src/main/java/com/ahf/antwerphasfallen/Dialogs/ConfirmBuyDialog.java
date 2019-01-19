package com.ahf.antwerphasfallen.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.Inventory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jorren on 24/12/2018.
 */

public class ConfirmBuyDialog extends DialogFragment {
    private static final String TAG = "ConfirmBuyDialog";

    private int itemId;
    private int price;

    private InGameActivity host;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            int tmp = getArguments().getInt("itemId");
            if(tmp != 0)
                itemId = tmp;
            tmp = getArguments().getInt("price");
            if(tmp != 0)
                price = tmp;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Confirm Purchase")
                .setMessage("Click the buy button to buy this item for " + price + " gold")
                .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //buy item
                        Call<Inventory> buy = InGameActivity.service.buyShopItem(itemId, host.CurrentTeam.getId());
                        buy.enqueue(new Callback<Inventory>() {
                            @Override
                            public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                                if (response.body() != null) {
                                    host.CurrentTeam.setInventory(response.body());
                                    host.loadTeam(host.CurrentTeam.getId());
                                    host.CheckIngredients();
                                }
                            }

                            @Override
                            public void onFailure(Call<Inventory> call, Throwable t) {
                                Toast.makeText(host, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
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
        if(context instanceof InGameActivity)
            host = (InGameActivity)context;
        else Log.e(TAG, "onAttach: context is not InGameActivity");
    }
}
