package com.ahf.antwerphasfallen.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ahf.antwerphasfallen.Helpers.GameDataService;
import com.ahf.antwerphasfallen.Helpers.RetrofitInstance;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Model.DAD;
import com.ahf.antwerphasfallen.R;
import java.util.ArrayList;
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
    private Button back;
    private String answer;
    private String difficulty;
    String location;
    String Dadsolution;

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
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            location = bundle.getString("target");


        }
        final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);
        Call<List<DAD>> call = service.getDadByName(location);
        call.enqueue(new Callback<List<DAD>>() {
            @Override
            public void onResponse(Call<List<DAD>> call, Response<List<DAD>> response) {
                List<DAD> dad = response.body();
                if(dad != null) {
                    question.setText(dad.get(0).getQuestion());
                    Dadsolution = dad.get(0).getCorrectOrder();
                    String[] options = dad.get(0).getAnswers().split(",");
                    t1.setText(options[0]);
                    t2.setText(options[1]);
                    t3.setText(options[2]);
                    t4.setText(options[3]);
                    difficulty = dad.get(0).getDifficulty();
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

        sp1 = (EditText) rootView.findViewById(R.id.Spinner1);
        sp2 = (EditText) rootView.findViewById(R.id.Spinner2);
        sp3 = (EditText) rootView.findViewById(R.id.Spinner3);
        sp4 = (EditText) rootView.findViewById(R.id.Spinner4);
        back = (Button) rootView.findViewById(R.id.btn_back);
        check = (Button) rootView.findViewById(R.id.dadsolution);
        question = (TextView) rootView.findViewById(R.id.question);
        t1 = (TextView) rootView.findViewById(R.id.Choice1);
        t2 = (TextView) rootView.findViewById(R.id.Choice2);
        t3 = (TextView) rootView.findViewById(R.id.Choice3);
        t4 = (TextView) rootView.findViewById(R.id.Choice4);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                host.opendad = false;
                host.ShowPuzzles(false);
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               List<String> answers = new ArrayList<>();
               List<String> order = new ArrayList<>();

                order.add(sp1.getText().toString());
                order.add(sp2.getText().toString());
                order.add(sp3.getText().toString());
                order.add(sp4.getText().toString());

                answers.add(t1.getText().toString());
                answers.add(t2.getText().toString());
                answers.add(t3.getText().toString());
                answers.add(t4.getText().toString());

                for (int i = 0;i<=3;i++)
                {
                    if(order.get(i).toString().equals("1") )
                    {
                        answer = answers.get(i).toString();

                    }
                }
                for (int i = 0;i<=3;i++)
                {
                    if(order.get(i).toString().equals("2"))
                    {
                        answer = answer + "," + answers.get(i).toString();
                    }
                }
                for (int i = 0;i<=3;i++)
                {
                    if(order.get(i).toString().equals("3"))
                    {
                        answer = answer + "," + answers.get(i).toString();
                    }
                }
                for (int i = 0;i<=3;i++)
                {
                    if(order.get(i).toString().equals("4"))
                    {
                        answer = answer + "," + answers.get(i).toString();
                    }
                }

                if (Dadsolution.equals(answer)){


                    Toast.makeText(host, "Correct",Toast.LENGTH_SHORT).show();
                    host.ReceiveReward(true,difficulty);
                    host.ShowPuzzles(false);
                }
                else {
                    Toast.makeText(host, "Wrong",Toast.LENGTH_SHORT).show();
                    host.ReceiveReward(false,difficulty);
                    host.ShowPuzzles(false);


                }
            }

        });
        return rootView;
    }
}
