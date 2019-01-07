package com.ahf.antwerphasfallen;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InGameActivity){
            this.listener = (InGameActivity) context;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
       Call<QuizPuzzles> call = service.GetQuestions();
       call.enqueue(new Callback<QuizPuzzles>() {
           @Override
           public void onResponse(Call<QuizPuzzles> call, Response<QuizPuzzles> response) {
               QuizPuzzles quiz = response.body();
               if (quiz != null) {
                   answer = quiz.getCorrectAnswer();
                   question.setText(quiz.getQuestion());
                   String[] answers = quiz.answers.split(",");
                   choice1.setText(answers[0]);
                   choice2.setText(answers[1]);
                   choice3.setText(answers[2]);

               }
           }


           @Override
           public void onFailure(Call<QuizPuzzles> call, Throwable t) {
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




                    // updateQuestion();
                    Toast.makeText(listener, "Correct",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(listener, "Wrong",Toast.LENGTH_SHORT).show();



                }
            }

        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice2.getText().equals(answer)){


                    // updateQuestion();
                    Toast.makeText(listener, "Correct",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(listener, "Wrong",Toast.LENGTH_SHORT).show();{

                }
            }

        });
        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice3.getText().equals(answer)){


                    // updateQuestion();
                    Toast.makeText(listener, "Correct",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(listener, "Wrong",Toast.LENGTH_SHORT).show();{

                    updateGold();

                }
            }

        });


        ;
        return rootView;

    }

    private void updateGold(){
        reward.setText(String.valueOf(gold));
    }
}


