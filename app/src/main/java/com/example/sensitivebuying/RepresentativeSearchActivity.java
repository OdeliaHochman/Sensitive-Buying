package com.example.sensitivebuying;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeSearchActivity extends AppCompatActivity {

  private SearchView mySearchView;

 private RecyclerView mRecycler;

    final String activity = "RepresentativeSearchActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_search);

        mySearchView = (SearchView)findViewById(R.id.searchLine);

        mRecycler = (RecyclerView)findViewById(R.id.recyclerView_products);

        new FirebaseDatabaseHelper().readProducts(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Product> productsList, List<String> keys) {
                findViewById(R.id.loading_products).setVisibility(View.GONE);
                new RecyclerView_config().setConfig(mRecycler,RepresentativeSearchActivity.this,productsList,keys);
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

    }


}
