package com.ahf.antwerphasfallen.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.ahf.antwerphasfallen.Helpers.GameDataService;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.R;
import com.ahf.antwerphasfallen.Helpers.RetrofitInstance;
import com.ahf.antwerphasfallen.Model.SubstitutionPuzzles;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubstitutionFragment extends Fragment {

private TextView key;
private EditText solution;
private String Key;
private Button Checksolution;
private String Solution;
private String Clear;
private TextView cleartext;
private String location;private String difficulty;

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
                if (solution.getText().toString().equals(Solution)){

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
        Bundle bundle = this.getArguments();
        if(bundle != null)
        {
            location = bundle.getString("target");
        }

        final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
        Call<List<SubstitutionPuzzles>> call = service.getSubsByName(location);
        call.enqueue(new Callback<List<SubstitutionPuzzles>>() {
            @Override
            public void onResponse(Call<List<SubstitutionPuzzles>> call, Response<List<SubstitutionPuzzles>> response) {
                List<SubstitutionPuzzles> sub = response.body();
                if (sub != null) {
                    Solution = sub.get(0).getSolution();
                    Clear = sub.get(0).getClearText();
                    Key = sub.get(0).getKey();
                    cleartext.setText(Clear);
                    key.setText(Key);
                    difficulty = sub.get(0).getDifficulty();
                }
            }

            @Override
            public void onFailure(Call<List<SubstitutionPuzzles>>call, Throwable t) {
            }
        });
    }
}
