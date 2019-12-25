package com.example.sensitivebuying.ui.customer;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sensitivebuying.firebaseHelper.FirebaseProductsHelper;
import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.R;
import com.example.sensitivebuying.RecyclerView_config;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements  View.OnClickListener {

    final String activity = " SearchFragment";
    private SearchView mySearchView;
    private RecyclerView mRecycler;
    private List<Product> productList;
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

        new FirebaseProductsHelper().readProducts(new FirebaseProductsHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Product> list, List<String> keys) {
                v.findViewById(R.id.progressBar_customer).setVisibility(View.GONE);
                productList=list;
                new RecyclerView_config().setConfig(mRecycler,getActivity(),productList,keys);
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


        if(mySearchView != null){
            mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }

        return  v;

    }




    @Override
    public void onClick(View v) {


    }

    private void search (String str) {

        ArrayList<Product> searchList = new ArrayList<>();
        ArrayList<String> searchKeys = new ArrayList<>();

        for ( Product p : productList) {
            if (p.getProductName().toLowerCase().contains(str) || p.getBarcode().contains(str))  {
                searchList.add(p);
                searchKeys.add(p.getBarcode());
            }
        }

        new RecyclerView_config().setConfig(mRecycler,getActivity(),searchList,searchKeys);


    }
}
