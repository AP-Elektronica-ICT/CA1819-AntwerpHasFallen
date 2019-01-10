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

public class StringListAdapter extends ArrayAdapter<String> {
    public StringListAdapter(@NonNull Context context, @NonNull ArrayList<String> objects) {
        super(context, -1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_item, null);

        TextView lblName = itemView.findViewById(R.id.lbl_ingredientName);
        //int x = position;
        String test = getItem(position);
        lblName.setText(getItem(position));
        return itemView;
    }

}
