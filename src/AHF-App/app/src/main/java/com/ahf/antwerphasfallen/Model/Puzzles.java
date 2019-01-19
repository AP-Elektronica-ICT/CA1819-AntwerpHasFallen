package com.ahf.antwerphasfallen.Model;
import android.content.Context;
import android.os.Bundle;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.R;

public class Puzzles extends Fragment {

    private Button quiz;
    private Button sub;
    private Button anagram;
    private Button Dad;
    private Boolean IsNew;
    private Boolean opensub;
    private Boolean opendad;
    private Boolean openquiz;
    private Boolean openanagram;

    private AlertDialog.Builder builder;
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
        leaveLocation = (Button) rootView.findViewById(R.id.btn_leaveLoc);
        anagram = rootView.findViewById(R.id.anagram);
        Dad = rootView.findViewById(R.id.Dad);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            IsNew = bundle.getBoolean("Stat");
            opensub = bundle.getBoolean("Sub");
            openquiz = bundle.getBoolean("Quiz");
            openanagram = bundle.getBoolean("Anagram");
            opendad = bundle.getBoolean("Dad");
        }

        if(openquiz) quiz.setTextColor(R.drawable.googleg_disabled_color_18);
        if(opensub) sub.setTextColor(R.drawable.googleg_disabled_color_18);
        if(openanagram) anagram.setTextColor(R.drawable.googleg_disabled_color_18);
        if(opendad) Dad.setTextColor(R.drawable.googleg_disabled_color_18);

        quiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (IsNew) {
                        openquiz = true;
                        QuizpuzzleAlert();
                    }
                    else if(!openquiz&&!IsNew) {
                        openquiz = true;
                        QuizpuzzleAlert();
                    }
                    {
                        quiz.setActivated(false);

                    }

                 }

           });
        sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (IsNew) {
                        opensub = true;
                        SubAlert();
                    }
                    else if(!opensub&&!IsNew) {
                        opensub = true;
                        SubAlert();
                    }

                    else{
                        sub.setActivated(false);

                    }

                }

            });
        anagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (IsNew) {
                    openanagram = true;
                    AnagramAlert();


                } else if(!openanagram&&!IsNew)
                {
                    openanagram = true;
                    AnagramAlert();
                }

                else {
                    anagram.setActivated(false);

                }
                }
            });

        Dad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (IsNew) {
                    opendad = true;
                    DADalert();

                }
                else if(!opendad&&!IsNew) {
                    opendad = true;
                    DADalert();

                }
                else
                {
                    Dad.setActivated(false);

                }
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

    private void QuizpuzzleAlert()
    {
        builder = new AlertDialog.Builder(listener);
        builder.setTitle("Before you start!")
                .setMessage("String")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        listener.ShowQuiz();
                    }
                });
        builder.show();
    }

    private void SubAlert()
    {
        builder = new AlertDialog.Builder(listener);
        builder.setTitle("Before you start!")
                .setMessage("String")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        listener.Showsub();
                    }
                });

        builder.show();
    }
    private void AnagramAlert()
    {
        builder = new AlertDialog.Builder(listener);
        builder.setTitle("Before you start!")
                .setMessage("String")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        listener.ShowAnagram();
                    }
                });

        builder.show();
    }
    private void DADalert()
    {
        builder = new AlertDialog.Builder(listener);
        builder.setTitle("Before you start!")
                .setMessage("String")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        listener.ShowDad();
                    }
                });
        builder.show();
    }





}



