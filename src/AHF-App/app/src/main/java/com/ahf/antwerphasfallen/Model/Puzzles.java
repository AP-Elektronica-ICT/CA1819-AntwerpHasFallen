package com.ahf.antwerphasfallen.Model;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.R;

public class Puzzles extends Fragment {

    private Button quiz;
    private Button sub;
    private Button leaveLocation;
    InGameActivity listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InGameActivity) {
            this.listener = (InGameActivity) context;
        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_puzzles, container, false);
        quiz = (Button) rootView.findViewById(R.id.Quiz);
        sub = (Button) rootView.findViewById(R.id.substitution);
        leaveLocation = (Button) rootView.findViewById(R.id.btn_leaveLoc) ;

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.ShowQuiz();

                }

                });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.Showsub();

            }

        });

        leaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.LeavePuzzles();
            }
        });




        return rootView;
    }
}



