package com.ahf.antwerphasfallen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
    private QuizPuzzles puzzles = new QuizPuzzles();
    private TextView question;
    private TextView reward;
    private Button choice1;
    private Button choice2;
    private Button choice3;

    private String answer;
    private int gold = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
        Call<QuizPuzzles> call = service.GetQuestions();
        call.enqueue(new Callback<QuizPuzzles>() {
            @Override
            public void onResponse(Call<QuizPuzzles> call, Response<QuizPuzzles> response) {
                QuizPuzzles quiz = response.body();
                if (quiz != null) {

                    answer = quiz.getCorrectAnswer();
                    Toast.makeText(QuizActivity.this, answer, Toast.LENGTH_SHORT).show();
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


        reward = (TextView) findViewById(R.id.gold);
        question = (TextView) findViewById(R.id.question);
        choice1 = (Button) findViewById(R.id.keuze1);
        choice2 = (Button) findViewById(R.id.keuze2);
        choice3 = (Button) findViewById(R.id.keuze3);
        //updateQuestion();

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice1.getText().equals(answer)) {
                    gold = gold + 5;


                    // updateQuestion();
                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                {
                    gold -= 1;

                }
            }

        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice2.getText().equals(answer)) {
                    gold = gold + 5;

                    // updateQuestion();
                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                {
                    gold -= 1;


                }
            }

        });
        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice3.getText().equals(answer)) {
                    gold = gold + 5;


                    // updateQuestion();
                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                    gold = gold - 1;

                }
            }
        });

    }
}

   /* private void updateQuestion(){
        question.setText(puzzles.getQuestion(questionnumber));
        choice1.setText(puzzles.getChoice1(questionnumber));
        choice2.setText(puzzles.getChoice2(questionnumber));
        choice3.setText(puzzles.getChoice3(questionnumber));
        answer = puzzles.getCorrectAnswer(questionnumber);
        questionnumber++;
    }
    */








