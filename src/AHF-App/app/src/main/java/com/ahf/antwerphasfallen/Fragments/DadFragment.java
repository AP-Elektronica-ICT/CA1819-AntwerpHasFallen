package com.ahf.antwerphasfallen.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ahf.antwerphasfallen.Helpers.GameDataService;
import com.ahf.antwerphasfallen.Helpers.RetrofitInstance;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.DAD;
import com.ahf.antwerphasfallen.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DadFragment extends Fragment {

    private EditText sp1;
    private EditText sp2;
    private EditText sp3;
    private EditText sp4;

    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView question;
    private Button check;

    String location = "MAS";
    String correctorder;
    String solution;

    private InGameActivity host;

    @Override
    public void onAttach(Context context )
    {
        super.onAttach(context);
        if (context instanceof InGameActivity)
        {
            this.host = (InGameActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
        Call<List<DAD>> call = service.getDadByName(location) ;
        call.enqueue(new Callback<List<DAD>>() {
            @Override
            public void onResponse(Call<List<DAD>> call, Response<List<DAD>> response) {
                List<DAD> dad = response.body();
                if (dad != null)
                {
                    question.setText(dad.get(0).getQuestion());
                    solution = dad.get(0).getCorrectOrder();
                    String [] options = dad.get(0).getAnswers().split(",");
                    t1.setText(options[0]);
                    t2.setText(options[1]);
                    t3.setText(options[2]);
                    t4.setText(options[3]);
                }
            }

            @Override
            public void onFailure(Call<List<DAD>> call, Throwable t) {

            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_dad,container,false);

        sp1 = (EditText) rootView.findViewById(R.id.keuze1);
        sp2 = (EditText) rootView.findViewById(R.id.keuze2);
        sp3 = (EditText) rootView.findViewById(R.id.keuze3);
        sp4 = (EditText) rootView.findViewById(R.id.keuze4);

        //sp1.get
        return rootView;
    }
}
