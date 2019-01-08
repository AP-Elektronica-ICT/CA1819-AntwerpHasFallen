package com.ahf.antwerphasfallen.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ahf.antwerphasfallen.Model.InventoryItem;
import com.ahf.antwerphasfallen.R;

import java.util.ArrayList;

/**
 * Created by Jorren on 16/11/2018.
 */

public class IngredientsListAdapter extends ArrayAdapter<InventoryItem> {
    public IngredientsListAdapter(@NonNull Context context, @NonNull ArrayList<InventoryItem> objects) {
        super(context, -1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_item_ingredient, null);

        TextView lblName = itemView.findViewById(R.id.lbl_name);
        TextView lblQuantity = itemView.findViewById(R.id.lbl_quantity);

        lblName.setText(getItem(position).getItem().getName());
        lblQuantity.setText(Integer.toString(getItem(position).getQuantity()));

        return itemView;
    }
}
