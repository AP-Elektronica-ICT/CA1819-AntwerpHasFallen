package com.ahf.antwerphasfallen;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
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

        EditText txtTeamname = (EditText)itemview.findViewById(R.id.txt_teamname);
        txtTeamname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getItem(position).setName(editable.toString());
            }
        });

        return itemview;
    }
}
