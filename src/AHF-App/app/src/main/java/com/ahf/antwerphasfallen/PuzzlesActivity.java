package com.ahf.antwerphasfallen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PuzzlesActivity extends AppCompatActivity {

    SubstitionPuzzles vraag = new SubstitionPuzzles();
    TextView answer;
    EditText question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles);
        vraag.setQuestion("Hoeveel is 2+2");
        vraag.setSolution("4");
        answer = (TextView) findViewById(R.id.textView2);
        question = (EditText) findViewById(R.id.editText);

        question.setText(vraag.question);

    }


    public void Checksolution(View view) {
        String antwoord = answer.getText().toString();
        if (antwoord == vraag.solution) {
            
        }
    }
}
