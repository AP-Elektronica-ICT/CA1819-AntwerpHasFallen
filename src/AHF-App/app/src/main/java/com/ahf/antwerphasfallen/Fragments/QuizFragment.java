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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizFragment extends Fragment {

    private QuizPuzzles puzzles = new QuizPuzzles();
    private TextView question;
    private TextView reward;
    private Button choice1;
    private Button choice2;
    private Button choice3;
    InGameActivity listener;
    private String answer;
    private int gold;
    private InGameActivity host;


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
       final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
       Call<List<QuizPuzzles>> call = service.GetQuestions();
       call.enqueue(new Callback<List<QuizPuzzles>>() {
           @Override
           public void onResponse(Call<List<QuizPuzzles>> call, Response<List<QuizPuzzles>> response) {
               List<QuizPuzzles> quiz = response.body();
               if (quiz != null) {
                   answer = quiz.get(0).getCorrectAnswer();
                   question.setText(quiz.get(0).getQuestion());
                   String[] answers = quiz.get(0).getAnswers().split(",");
                   choice1.setText(answers[0]);
                   choice2.setText(answers[1]);
                   choice3.setText(answers[2]);

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

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice1.getText().equals(answer)){

                    updateGold(true);




                    Toast.makeText(host, "Correct",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(host, "Wrong",Toast.LENGTH_SHORT).show();
                    updateGold(false);



                }
            }

        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice2.getText().equals(answer)){

                    updateGold(true);


                    Toast.makeText(host, "Correct",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(host, "Wrong",Toast.LENGTH_SHORT).show();
                    updateGold(false);

                }
            }

        });
        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice3.getText().equals(answer)){
                    updateGold(true);


                    Toast.makeText(host, "Correct",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(host, "Wrong",Toast.LENGTH_SHORT).show();
                        updateGold(false);

                }
            }

        });


        ;
        return rootView;

    }

    private void updateGold(boolean status)
    {
        final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);


    Call<QuizPuzzles> call = service.updatePrice(status,host.CurrentTeam.getId());
        call.enqueue(new Callback<QuizPuzzles>() {
            @Override
            public void onResponse(Call<QuizPuzzles> call, Response<QuizPuzzles> response) {
                QuizPuzzles quiz = response.body();

            }


            @Override
            public void onFailure(Call<QuizPuzzles> call, Throwable t) {
                t.printStackTrace();
            }



        });


        host.ShowPuzzles(false);





    }
}


