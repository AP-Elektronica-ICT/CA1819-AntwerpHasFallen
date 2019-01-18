package com.ahf.antwerphasfallen.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ahf.antwerphasfallen.Helpers.GameDataService;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.R;
import com.ahf.antwerphasfallen.Helpers.RetrofitInstance;
import com.ahf.antwerphasfallen.Model.ShopItem;
import com.ahf.antwerphasfallen.Adapters.ShopListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class    ShopFragment extends Fragment {

    private InGameActivity host;
    public static final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);

    private ListView lv_shopItems;
    private ArrayList<ShopItem> shopItems;
    private ShopListAdapter shopListAdapter;

    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_shop, container, false);

        lv_shopItems = fragmentView.findViewById(R.id.lv_shop_shopItems);
        LoadItems();

        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InGameActivity) {
            host = (InGameActivity) context;
            setAdapters();
        } else {
            throw new RuntimeException(context.toString()
                    + " is not inGameActivity");
        }
    }

    public void setAdapters() {
        if (lv_shopItems != null && shopListAdapter != null) {
            lv_shopItems.setAdapter(shopListAdapter);
            shopListAdapter.notifyDataSetChanged();
        }
    }

    public void LoadItems() {
        Call<ArrayList<ShopItem>> getList = service.getShopItems();
        getList.enqueue(new Callback<ArrayList<ShopItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ShopItem>> call, Response<ArrayList<ShopItem>> response) {
                if(response.body() != null){
                    shopItems = response.body();
                    shopListAdapter = new ShopListAdapter(getContext(), shopItems);
                    setAdapters();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ShopItem>> call, Throwable t) {
                Toast.makeText(host, "Failed getting items", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
