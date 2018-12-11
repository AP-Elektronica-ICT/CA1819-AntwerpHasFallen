package com.ahf.antwerphasfallen;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TeamFragment extends Fragment {

    private InGameActivity listener;

    private int gameId;
    private String teamName;
    private TextView txtGame;
    private TextView txtTeamName;

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

        txtGame = rootView.findViewById(R.id.txt_game);
        txtTeamName = rootView.findViewById(R.id.txt_team);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            gameId = bundle.getInt("gameId");
            teamName = bundle.getString("teamName");
        }

        txtGame.setText(String.valueOf(gameId));
        txtTeamName.setText(teamName);



        return rootView;
    }
}
