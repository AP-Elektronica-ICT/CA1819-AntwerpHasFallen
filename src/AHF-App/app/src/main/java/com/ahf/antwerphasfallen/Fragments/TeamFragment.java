package com.ahf.antwerphasfallen.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ahf.antwerphasfallen.Adapters.IngredientsListAdapter;
import com.ahf.antwerphasfallen.Adapters.ItemListAdapter;
import com.ahf.antwerphasfallen.Adapters.StringListAdapter;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.InventoryItem;
import com.ahf.antwerphasfallen.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TeamFragment extends Fragment {

    private InGameActivity listener;

    private int gameId;
    private String teamName;
    private TextView txtGame;
    private TextView txtTeamName;
    private ArrayList<String> missingIngredients;
    private ListView lvMissingIngredients;
    private StringListAdapter ingredientsAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InGameActivity){
            this.listener = (InGameActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            gameId = bundle.getInt("gameId");
            teamName = bundle.getString("teamName");
            missingIngredients = bundle.getStringArrayList("ingredients");
        }

        txtGame = rootView.findViewById(R.id.txt_game);
        txtTeamName = rootView.findViewById(R.id.txt_team);

        ingredientsAdapter = new StringListAdapter(listener, (ArrayList) missingIngredients);

        lvMissingIngredients = rootView.findViewById(R.id.lv_mingredients);
        lvMissingIngredients.setAdapter(ingredientsAdapter);
        ingredientsAdapter.notifyDataSetChanged();
        txtGame.setText(String.valueOf(gameId));
        txtTeamName.setText(teamName);



        return rootView;
    }
}
