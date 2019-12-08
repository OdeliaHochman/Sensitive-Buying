package com.example.sensitivebuying;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RepresentativeSearchActivity extends AppCompatActivity {

private SearchView mySearchView;
private ListView myListView;
private ArrayList<Products> productsList;
private ArrayAdapter<Products> adapter; /////////????? or string
//private ProductsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_search);

        mySearchView = (SearchView)findViewById(R.id.searchLine);
        myListView = (ListView) findViewById(R.id.list_item);
 //      adapter = new ProductsAdapter(this, productsList);
//        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,productsList);
 //       myListView.setAdapter(adapter);

//        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });

// To link to an item click we will add the following function:

//        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(this, Activity3.class);
//                startActivity(intent);
//            }
//        });

    }


}
