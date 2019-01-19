package com.ahf.antwerphasfallen.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahf.antwerphasfallen.Helpers.GameDataService;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.QuizPuzzles;
import com.ahf.antwerphasfallen.R;
import com.ahf.antwerphasfallen.Helpers.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizFragment extends Fragment {

    private TextView question;
    private TextView reward;
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private String answer;
    private Integer questionnumber =0;
    private String location;
    private InGameActivity host;
    private List<String> answers = new ArrayList<>();
    private List<String> questions = new ArrayList<>();
    private List <String> correctanswers= new ArrayList<>();
    private List <String> difficulty = new ArrayList<>();




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InGameActivity){
            this.host = (InGameActivity) context;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            location = bundle.getString("target");
        }
        final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
        Call<List<QuizPuzzles>> call = service.getQuizByName(location);
        call.enqueue(new Callback<List<QuizPuzzles>>() {
           @Override
           public void onResponse(Call<List<QuizPuzzles>> call, Response<List<QuizPuzzles>> response) {
               List<QuizPuzzles> quiz = response.body();
               if (quiz != null) {



                   answers.add(quiz.get(0).getAnswers());
                   answers.add(quiz.get(1).getAnswers());
                   questions.add(quiz.get(0).getQuestion());
                   questions.add(quiz.get(1).getQuestion());
                   correctanswers.add(quiz.get(0).getCorrectAnswer());
                   correctanswers.add(quiz.get(1).getCorrectAnswer());
                   difficulty.add(quiz.get(0).getDifficulty());
                   difficulty.add(quiz.get(1).getDifficulty());
               }
           }

           @Override
           public void onFailure(Call<List<QuizPuzzles>> call, Throwable t) {
                t.printStackTrace();
           }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        reward = (TextView) rootView.findViewById(R.id.gold);
        question = (TextView) rootView.findViewById(R.id.question);
        choice1 = (Button) rootView.findViewById(R.id.keuze1);
        choice2 = (Button) rootView.findViewById(R.id.keuze2);
        choice3 = (Button) rootView.findViewById(R.id.keuze3);

        updateQuestion();






        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice1.getText().equals(answer)){

                    updateQuestion();

                    Toast.makeText(host, "Correct",Toast.LENGTH_SHORT).show();
                }
                else {
                    updateQuestion();
                    Toast.makeText(host, "Wrong",Toast.LENGTH_SHORT).show();

                    }

                }

        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice2.getText().equals(answer)){

                    updateQuestion();

                    Toast.makeText(host, "Correct",Toast.LENGTH_SHORT).show();
                }
                else {
                    updateQuestion();
                    Toast.makeText(host, "Wrong",Toast.LENGTH_SHORT).show();

                }
            }

        });
        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice3.getText().equals(answer)){

                    updateQuestion();

                    Toast.makeText(host, "Correct",Toast.LENGTH_SHORT).show();
                }
                else {
                    updateQuestion();
                    Toast.makeText(host, "Wrong",Toast.LENGTH_SHORT).show();

                }
            }

        });
        return rootView;
    }

    private void updateQuestion()
    {
        if (questionnumber >=1) {

            question.setText(questions.get(questionnumber));
            String[] choices = answers.get(questionnumber).split(",");
            choice1.setText(choices[0]);
            choice2.setText(choices[1]);
            choice3.setText(choices[2]);

            answer = correctanswers.get(questionnumber);
        }
        else host.ShowPuzzles(false);
    }

    }


