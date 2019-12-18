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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepresentativeSearchActivity extends AppCompatActivity implements View.OnClickListener  {

    private SearchView mySearchView;

 private RecyclerView mRecycler;
 private FloatingActionButton floatingButton;
 private TextView textView;
 private List<Product> productList;

    final String activity = "RepresentativeSearchActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_search);

        mySearchView = (SearchView)findViewById(R.id.searchLine);

        mRecycler = (RecyclerView)findViewById(R.id.recyclerView_products);

        floatingButton = (FloatingActionButton)findViewById(R.id.floating_button_search);
        floatingButton.setOnClickListener(this);

        new FirebaseDatabaseHelper().readProducts(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Product> productsList, List<String> keys) {
               findViewById(R.id.progressBar).setVisibility(View.GONE);
               productList=productsList;
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


//         adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,productsList);
//       myListView.setAdapter(adapter);
//         mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//             @Override
//             public boolean onQueryTextSubmit(String query) {
//                 textView.setText(query);
//                 return false;
//             }
//
//             @Override
//             public boolean onQueryTextChange(String newText) {
//                 //adapter.getFilter().filter(newText);
//                 textView.setText(newText);
//                 return false;
//             }
//         });


    }

    @Override
    public void onClick(View v) {

        if(v == floatingButton)
        {
            Intent intent = new Intent(RepresentativeSearchActivity.this,RepresentativeAddProductActivity.class);
            startActivity(intent);
        }
    }
}
