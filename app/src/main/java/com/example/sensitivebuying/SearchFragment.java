package com.example.sensitivebuying;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements  View.OnClickListener {

    final String activity = " SearchFragment";
    private SearchView mySearchView;
    private RecyclerView mRecycler;
    private  View v;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("debug",activity);
        v= inflater.inflate(R.layout.fragment_search, container, false);

        mySearchView = (SearchView)v.findViewById(R.id.searchLine_customer);
        mRecycler = (RecyclerView)v.findViewById(R.id.recyclerView_products_customer);

        new FirebaseDatabaseHelper().readProducts(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Product> productsList, List<String> keys) {
                v.findViewById(R.id.progressBar_customer).setVisibility(View.GONE);
                new RecyclerView_config().setConfig(mRecycler,getActivity(),productsList,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
        return  v;

    }






/*
         adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,productsList);
        myListView.setAdapter(adapter);
         mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 adapter.getFilter().filter(newText);
                 return false;
             }
         });
  To link to an item click we will add the following function:
         myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent(this, Activity3.class);
                 startActivity(intent);
             }
         });
 */



    @Override
    public void onClick(View v) {


    }
}
