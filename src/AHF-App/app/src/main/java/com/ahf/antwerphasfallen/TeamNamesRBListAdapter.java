package com.ahf.antwerphasfallen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.ArrayList;

/**
 * Created by Jorren on 28/10/2018.
 */

public class TeamNamesRBListAdapter extends ArrayAdapter<Team> {

    public TeamNamesRBListAdapter(@NonNull Context context, @NonNull ArrayList<Team> objects) {
        super(context, -1, objects);
    }

    private int selectedPosition = 0;

    public int getSelectedPosition(){
        return selectedPosition;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.list_item_rb_teamname, null);

        RadioButton rbTeamname = (RadioButton)itemview.findViewById(R.id.rb_teamname);
        rbTeamname.setText(getItem(position).getName());
        rbTeamname.setChecked(position == selectedPosition);
        rbTeamname.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    selectedPosition = position;
                    notifyDataSetChanged();
                }
            }
        });

        return itemview;
    }
}
