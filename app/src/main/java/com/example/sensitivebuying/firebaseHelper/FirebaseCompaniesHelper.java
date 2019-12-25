package com.example.sensitivebuying.firebaseHelper;

import androidx.annotation.NonNull;

import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.dataObject.RepresentativeUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class FirebaseCompaniesHelper
{
    private FirebaseDatabase cDatabase;
    private DatabaseReference cReference;
    private RepresentativeUser user;
    private List<Product> productsList = new ArrayList<>();

    public interface DataStatus
    {
        void DataIsLoaded(List<Product> productsList , List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseCompaniesHelper()
    {
        //cDatabase =FirebaseDatabase.getInstance();
        //cReference=cDatabase.getReference("Companies");
        cReference=FirebaseDatabase.getInstance().getReference().child("Companies");
    }

    public void readProductsOfCompanie(final DataStatus dataStatus)
    {
        cReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                productsList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    Product product = keyNode.getValue(Product.class);
                    productsList.add(product);
                }
                dataStatus.DataIsLoaded(productsList,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
    public void addProductOfCompanies(Product product,final DataStatus dataStatus)
    {
        String  barcodeCompany = product.getBarcode();
        cReference.child(product.getCompanyName()).push().setValue(barcodeCompany).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }
    public void deleteProduct(String key , final DataStatus dataStatus)
    {
        cReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }

}
