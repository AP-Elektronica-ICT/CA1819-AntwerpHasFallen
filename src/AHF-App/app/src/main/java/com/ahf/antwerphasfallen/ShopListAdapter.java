package com.ahf.antwerphasfallen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jorren on 24/12/2018.
 */

public class ShopListAdapter extends ArrayAdapter<ShopItem> {

    public ShopListAdapter(@NonNull Context context, @NonNull ArrayList<ShopItem> objects) {
        super(context, -1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_item_shop_item, null);

        TextView lblName = itemView.findViewById(R.id.lbl_shopItem_name);
        TextView lblDescription = itemView.findViewById(R.id.lbl_shopItem_description);
        Button btnPrice = itemView.findViewById(R.id.btn_shopItem_price);

        lblName.setText(getItem(position).getItem().getName());
        btnPrice.setText(Integer.toString(getItem(position).getPrice()));

        return itemView;
    }
}
