package com.ahf.antwerphasfallen.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ahf.antwerphasfallen.Dialogs.JoinTeamDialog;
import com.ahf.antwerphasfallen.Dialogs.TargetTeamDialog;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.InventoryItem;
import com.ahf.antwerphasfallen.Model.Item;
import com.ahf.antwerphasfallen.R;

import java.util.ArrayList;

/**
 * Created by Jorren on 16/11/2018.
 */

public class ItemListAdapter extends ArrayAdapter<InventoryItem> {
    private InGameActivity host;

    public ItemListAdapter(@NonNull Context context, @NonNull ArrayList<InventoryItem> objects) {
        super(context, -1, objects);
        if(context instanceof InGameActivity)
            host = (InGameActivity)context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_item_item, null);

        TextView lblName = itemView.findViewById(R.id.lbl_item_name);
        TextView lblQuantity = itemView.findViewById(R.id.lbl_item_quantity);
        TextView lblDescription = itemView.findViewById(R.id.lbl_item_description);
        Button btnUse = itemView.findViewById(R.id.btn_use);

        Item item = getItem(position).getItem();
        lblName.setText(item.getName());
        lblQuantity.setText(Integer.toString(getItem(position).getQuantity()));
        lblDescription.setText(item.getDescription());

        btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TargetTeamDialog dialog = new TargetTeamDialog();
                Bundle data = new Bundle();
                data.putInt("gameId", host.CurrentPlayer.getGameId());
                data.putInt(TargetTeamDialog.ITEM_ID, getItem(position).getId());
                data.putString(JoinTeamDialog.TITLE_TEXT, "Choose the team you want to target with this item");
                data.putString(JoinTeamDialog.POSITIVE_BTN_TEXT, "Use");
                dialog.setArguments(data);
                dialog.show(host.getSupportFragmentManager(), "Choose target");
            }
        });

        return itemView;
    }
}
