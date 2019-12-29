package com.example.sensitivebuying.ui.customer;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sensitivebuying.dataObject.Sensitive;
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsBySensitiveHelper;
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsHelper;
import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.R;
import com.example.sensitivebuying.RecyclerView_config;
import com.example.sensitivebuying.firebaseHelper.FirebaseSenstiveUserHelper;

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
    List<String> barcodes;
    private List<String> forbiddenBarcode= new ArrayList<>();
    ArrayList<Sensitive> senstiveOfUser ;
    private int num;


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
                barcodes=keys;
                ///
                //Product p = new Product("11","22","7290000066318","fd","des","pic",null);
                //public Product(String companyName, String productName, String barcode, String weightAndType, String productDescription, String urlImage, ArrayList<Sensitive> sensitiveList)
               // List<Product> products = new ArrayList<>();
               // products.add(p);
                forbiddenSen();
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
                    if (mySearchView!= null ) {
                        Log.d("debug_search",""+newText);
                        search(newText);
                        return true;
                    }
                    return false;
                }
            });
        }

        return  v;

    }


    private void forbiddenSen () {

        new FirebaseSenstiveUserHelper().readSensitive(new FirebaseSenstiveUserHelper.DataStatus() {


            @Override
            public void DataIsLoaded(ArrayList<Sensitive> sensitives, ArrayList<String> keys) {
                senstiveOfUser=sensitives;
                forbiddenBarcode.clear();
                getBarcodesFromSen(senstiveOfUser);
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

    private void getBarcodesFromSen (final ArrayList <Sensitive> sensitives) {
        for ( Sensitive s : sensitives) {
            new FirebaseProductsBySensitiveHelper().readProductsOfSen(s, new FirebaseProductsBySensitiveHelper.DataStatus() {
                @Override
                public void DataIsLoaded(ArrayList<String> CurBarcodes) {
                    num++;
                    if (CurBarcodes != null)
                        forbiddenBarcode.addAll(CurBarcodes);
                    if (num==sensitives.size()) {
                        num=0;
                        new RecyclerView_config().setConfigCus(mRecycler, getActivity(), productList, barcodes, forbiddenBarcode);
                    }
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


    @Override
    public void onClick(View v) {


    }

    private void search (String str) {

        ArrayList<Product> searchList = new ArrayList<>();
        ArrayList<String> searchKeys = new ArrayList<>();
        if (productList!= null ) {
            for (Product p : productList) {
                if (p.getProductName().toLowerCase().contains(str) || p.getBarcode().contains(str)) {
                    searchList.add(p);
                    searchKeys.add(p.getBarcode());
                }
            }


            new RecyclerView_config().setConfigCus(mRecycler, getActivity(), searchList, searchKeys, forbiddenBarcode);

        }

    }
}
