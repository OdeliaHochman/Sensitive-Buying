package com.example.sensitivebuying.ui.represntative;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sensitivebuying.dataObject.RepresentativeUser;
import com.example.sensitivebuying.dataObject.User;
import com.example.sensitivebuying.firebaseHelper.FirebaseCompaniesHelper;
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsHelper;
import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.R;
import com.example.sensitivebuying.RecyclerView_config;
import com.example.sensitivebuying.firebaseHelper.FirebaseUserHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeSearchActivity extends AppCompatActivity implements View.OnClickListener  {

    private SearchView mySearchView;
    private RecyclerView mRecycler;
    private FloatingActionButton floatingButton;
    private List<Product> productList;
    private RepresentativeUser repUser;
    private FirebaseDatabase pDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference pReference=pDatabase.getReference("Products");
    private  String companyName;

    private void DisplayProductsOfComp(String userRep)
    {

    }

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

        new FirebaseUserHelper().readUser(new FirebaseUserHelper.DataStatusUser() {
            @Override
            public void DataIsLoaded(User userHelper, String key) {
                repUser = (RepresentativeUser) userHelper;
                companyName = repUser.getCompanyName();
                ShowProductsByBarcodes();
            }
            });


        if(mySearchView != null){
            mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (mySearchView!= null ) {
                        Log.d("debug_search",""+newText);
                        search(newText);
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public void onClick(View v) {

        if(v == floatingButton)
        {
            Intent intent = new Intent(RepresentativeSearchActivity.this,RepresentativeAddProductActivity.class);
            startActivity(intent);
        }
    }

    private void search(String str) {

        ArrayList<Product>searchList = new ArrayList<>();
        ArrayList<String> searchKeys = new ArrayList<>();
        if (productList!= null ) {
            for (Product p : productList) {
                if (p.getProductName().toLowerCase().contains(str) || p.getBarcode().contains(str)) {
                    searchList.add(p);
                    searchKeys.add(p.getBarcode());
                }
            }

            new RecyclerView_config().setConfig(mRecycler, RepresentativeSearchActivity.this, searchList, searchKeys);

        }
    }

    private void ShowProductsByBarcodes()
    {
        new FirebaseCompaniesHelper().readProductsOfCompanie(companyName,new FirebaseCompaniesHelper.DataStatus()
        {
            @Override
            public void DataIsLoaded(List<String> barcodesList)
            {
                new FirebaseProductsHelper().readProductByBarcode(barcodesList, new FirebaseProductsHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Product> ProductBarcode, List<String> keys) {
                        findViewById(R.id.progressBar).setVisibility(View.GONE);

                        productList=ProductBarcode;
                        new RecyclerView_config().setConfig(mRecycler,RepresentativeSearchActivity.this,productList,keys);

                    }

                    @Override
                    public void ProductDataLoaded(Product product) {

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


    }
}
