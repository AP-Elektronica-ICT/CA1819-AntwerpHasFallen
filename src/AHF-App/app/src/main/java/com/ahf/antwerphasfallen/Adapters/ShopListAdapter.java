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

import com.ahf.antwerphasfallen.Dialogs.ConfirmBuyDialog;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.ShopItem;
import com.ahf.antwerphasfallen.R;

import java.util.ArrayList;

/**
 * Created by Jorren on 24/12/2018.
 */

public class ShopListAdapter extends ArrayAdapter<ShopItem> {

    private InGameActivity host;

    public ShopListAdapter(@NonNull Context context, @NonNull ArrayList<ShopItem> objects) {
        super(context, -1, objects);
        if(context instanceof InGameActivity)
            host = (InGameActivity)context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_item_shop_item, null);

        final ShopItem item = getItem(position);

        TextView lblName = itemView.findViewById(R.id.lbl_shopItem_name);
        TextView lblDescription = itemView.findViewById(R.id.lbl_shopItem_description);
        Button btnPrice = itemView.findViewById(R.id.btn_shopItem_price);

        lblName.setText(item.getItem().getName());
        lblDescription.setText(item.getItem().getDescription());
        btnPrice.setText(Integer.toString(item.getPrice()));
        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmBuyDialog dialog = new ConfirmBuyDialog();
                Bundle data = new Bundle();
                data.putInt("itemId", item.getItem().getId());
                data.putInt("price", item.getPrice());
                dialog.setArguments(data);
                dialog.show(host.getFragmentManager(), "confirm buy ");
            }
        });
        if(host.CurrentTeam.getMoney() - item.getPrice() < 0) {
            btnPrice.setEnabled(false);
            btnPrice.setTextColor(getContext().getResources().getColor(R.color.red));
        }
        return itemView;
    }
}
