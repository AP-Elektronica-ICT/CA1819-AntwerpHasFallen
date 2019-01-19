package com.ahf.antwerphasfallen.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.Adapters.IngredientsListAdapter;
import com.ahf.antwerphasfallen.Model.Inventory;
import com.ahf.antwerphasfallen.Model.InventoryItem;
import com.ahf.antwerphasfallen.Adapters.ItemListAdapter;
import com.ahf.antwerphasfallen.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass
 * Use the {@link InventoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InventoryFragment extends Fragment {

    private InGameActivity host;
    private IngredientsListAdapter ingredientsAdapter;
    private ItemListAdapter shopInventoryAdapter;
    private ListView lvIngredients;
    private ListView lvShopItems;

    public InventoryFragment() {
    }

    public static InventoryFragment newInstance() {
        InventoryFragment fragment = new InventoryFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public void setAdapters() {
        if (host != null) {
            if (host.CurrentTeam != null) {
                if (host.CurrentTeam.getInventory() != null) {
                    if (host.CurrentTeam.getInventory().getIngredients() != null && ingredientsAdapter == null) {
                        ingredientsAdapter = new IngredientsListAdapter(getContext(), (ArrayList) host.CurrentTeam.getInventory().getIngredients());
                        lvIngredients.setAdapter(ingredientsAdapter);
                    }
                    else{
                        ingredientsAdapter.clear();
                        ingredientsAdapter.addAll(host.CurrentTeam.getInventory().getIngredients());
                        ingredientsAdapter.notifyDataSetChanged();
                    }
                    if (host.CurrentTeam.getInventory().getItems() != null && shopInventoryAdapter == null) {
                        shopInventoryAdapter = new ItemListAdapter(getContext(), (ArrayList) host.CurrentTeam.getInventory().getItems());
                        lvShopItems.setAdapter(shopInventoryAdapter);
                    }
                    else{
                        shopInventoryAdapter.clear();
                        shopInventoryAdapter.addAll(host.CurrentTeam.getInventory().getItems());
                        shopInventoryAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                ingredientsAdapter = new IngredientsListAdapter(getContext(), new ArrayList<InventoryItem>());
                shopInventoryAdapter = new ItemListAdapter(getContext(), new ArrayList<InventoryItem>());
            }
//            lvIngredients.setAdapter(ingredientsAdapter);
//            lvShopItems.setAdapter(shopInventoryAdapter);
        }
    }

    public void UpdateInventory(){
        if(host != null)
            if(host.CurrentTeam != null)
                if(host.CurrentTeam.getInventory() != null){
                    Call<Inventory> inventoryCall = InGameActivity.service.getInventory(host.CurrentTeam.getInventory().getId());
                    inventoryCall.enqueue(new Callback<Inventory>() {
                        @Override
                        public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                            if(response.body() != null){
                                host.CurrentTeam.setInventory(response.body());
                                host.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setAdapters();
                                    }
                                });
                            }
                            else
                                Toast.makeText(host, "Failed getting inventory", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Inventory> call, Throwable t) {
                            Toast.makeText(host, "Failed getting inventory", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_inventory, container, false);

        lvIngredients = fragmentView.findViewById(R.id.lv_ingredients);
        lvIngredients.setAdapter(ingredientsAdapter);

        lvShopItems = fragmentView.findViewById(R.id.lv_shopItems);
        lvShopItems.setAdapter(shopInventoryAdapter);

        UpdateInventory();

        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InGameActivity) {
            host = (InGameActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " is not inGameActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        host = null;
    }
}
