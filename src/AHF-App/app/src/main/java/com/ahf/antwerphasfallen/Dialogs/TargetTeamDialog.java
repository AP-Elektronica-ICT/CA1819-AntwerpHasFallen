package com.ahf.antwerphasfallen.Dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.Inventory;
import com.ahf.antwerphasfallen.Model.Team;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jorren on 11/01/2019.
 */

public class TargetTeamDialog extends JoinTeamDialog {
    private static final String TAG = "TargetTeamDialog";
    public static final String ITEM_ID = "itemId";

    private InGameActivity host;

    @Override
    protected void positiveButtonClick(DialogInterface dialogInterface, int i, Team targetTeam) {
        int itemId = 0;
        if (getArguments() != null) {
            if (getArguments().getInt(TargetTeamDialog.ITEM_ID) != 0)
                itemId = getArguments().getInt(TargetTeamDialog.ITEM_ID);
        }
        //Team targetTeam = adapter.getItem(adapter.getSelectedPosition());
        if (itemId != 0 && targetTeam != null) {
            Call<Inventory> inventoryCall = InGameActivity.service.useShopItem(host.CurrentTeam.getId(), itemId, targetTeam.getId());
            inventoryCall.enqueue(new Callback<Inventory>() {
                @Override
                public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                    if (response.body() != null)
                        host.CurrentTeam.setInventory(response.body());
                    else if (response.code() == 404)
                        Toast.makeText(host, "This item cannot be used", Toast.LENGTH_SHORT).show();
                    host.loadTeam(host.CurrentTeam.getId());
                }

                @Override
                public void onFailure(Call<Inventory> call, Throwable t) {
                    Log.e(TAG, "onFailure: Use of item failed");
                    Toast.makeText(host, "Use of item failed\nPlease try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InGameActivity)
            host = (InGameActivity) context;
        else Log.e(TAG, "onAttach: context is not InGameActivity");
    }
}
