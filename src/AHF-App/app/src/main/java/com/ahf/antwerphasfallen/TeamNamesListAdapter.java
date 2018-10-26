package com.ahf.antwerphasfallen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorren on 21/10/2018.
 */

public class TeamNamesListAdapter extends ArrayAdapter<Team> {
    private static final String TAG = "TeamNamesListAdapter";

    private Context context;

    public TeamNamesListAdapter(@NonNull Context context, @NonNull ArrayList<Team> objects) {
        super(context, -1, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.list_item_teamname, null);

        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(context.getResources().getColor(R.color.transparant));

        final EditText txtTeamname = (EditText)itemview.findViewById(R.id.txt_teamname);
        txtTeamname.setContentDescription("teamname " + (position + 1));
        txtTeamname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean different = true;
                for(int i = 0; i < getCount(); i++ ){
                    if(editable.toString().equals(getItem(i).getName()) && i != position){
                        different = false;
                    }
                }
                if(different){
                    getItem(position).setName(editable.toString());
                    gradientDrawable.setStroke(0, context.getResources().getColor(R.color.red));
                }
                else{
                    gradientDrawable.setStroke(5,context.getResources().getColor(R.color.red));
                }
                txtTeamname.setBackground(gradientDrawable);
            }
        });

        return itemview;
    }
}
