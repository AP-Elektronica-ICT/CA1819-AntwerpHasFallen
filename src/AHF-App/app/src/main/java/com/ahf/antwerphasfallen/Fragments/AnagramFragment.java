package com.ahf.antwerphasfallen.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ahf.antwerphasfallen.Adapters.IngredientsListAdapter;
import com.ahf.antwerphasfallen.Helpers.GameDataService;
import com.ahf.antwerphasfallen.Helpers.RetrofitInstance;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.Anagrams;
import com.ahf.antwerphasfallen.R;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnagramFragment extends Fragment  {

    private TextView scrambled;
    String Scrambled;
    private EditText solution;
    String Solution;
    private TextView hint;
    String Hint;
    private Button check;
    private InGameActivity host;
    private String location = "MAS";


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof InGameActivity){
            this.host = (InGameActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
        Call<List<Anagrams>> call = service.getAnagramByName(location);
        call.enqueue(new Callback<List<Anagrams>>() {
            @Override
            public void onResponse(Call<List<Anagrams>> call, Response<List<Anagrams>> response) {
                List<Anagrams> anagram = response.body();
                Scrambled = anagram.get(0).getScrambled();
                scrambled.setText(Scrambled);
                Hint = anagram.get(0).getTip();
                hint.setText(Hint);
                Solution = anagram.get(0).getSollution();

            }

            @Override
            public void onFailure(Call<List<Anagrams>> call, Throwable t) {

            }
        });

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_anagram,container,false);
        scrambled = (TextView) rootView.findViewById(R.id.anagram);
        hint = (TextView) rootView.findViewById(R.id.tip);
        check = (Button) rootView.findViewById(R.id.btnsol);
        solution = (EditText) rootView.findViewById(R.id.editSolution);


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (solution.getText().equals(Solution))
                {
                    Toast.makeText(host,"Correct",Toast.LENGTH_SHORT);
                    host.ShowPuzzles(false);
                }

            }



        });

        return rootView;


    }





}
