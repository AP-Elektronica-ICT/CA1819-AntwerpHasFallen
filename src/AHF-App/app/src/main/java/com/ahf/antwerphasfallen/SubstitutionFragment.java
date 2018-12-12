package com.ahf.antwerphasfallen;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubstitutionFragment extends Fragment {

private SubstitionPuzzles puzzles = new SubstitionPuzzles();
private TextView key;
private EditText solution;
private String Key;
private Button Checksolution;
private String Solution;
private String Clear;
private TextView cleartext;

InGameActivity listener;


@Override
    public void onAttach(Context context){
    super.onAttach(context);
    if (context instanceof InGameActivity){
        this.listener = (InGameActivity) context;
    }
}


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_substitution, container, false);
        key = (TextView) rootView.findViewById(R.id.key);
        Checksolution = (Button) rootView.findViewById(R.id.checksolution);
        cleartext = (TextView) rootView.findViewById(R.id.clear);
        solution = (EditText) rootView.findViewById(R.id.solution);
        Checksolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (solution.getText().equals(Solution)){

                    // updateQuestion();
                    Toast.makeText(listener, "Correct",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(listener, "Wrong",Toast.LENGTH_SHORT).show();


                }
            }

        });


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceSate) {
    super.onCreate(savedInstanceSate);
    final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
    Call<SubstitutionPuzzles> call = service.getQuestionbyId(1);
    call.enqueue(new Callback<SubstitutionPuzzles>() {
        @Override
        public void onResponse(Call<SubstitutionPuzzles> call, Response<SubstitutionPuzzles> response) {
            SubstitutionPuzzles sub = response.body();
            if (sub != null) {
                Key = sub.getKey();
                Solution = sub.getSolution();
                Clear = sub.getClearText();
                cleartext.setText(Clear);
                key.setText(Key);


            }
        }

        @Override
        public void onFailure(Call<SubstitutionPuzzles> call, Throwable t) {

        }


    });



}

}
