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
    private List<Product> companiesList = new ArrayList<>();

    public interface DataStatus
    {
        void DataIsLoaded(List<String> barcodesList);
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

    public void readProductsOfCompanie(String nameComp,final DataStatus dataStatus)
    {
        cReference.child(nameComp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                List<String> barcodes = new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren())
                {
                    barcodes.add(keyNode.getValue(String.class));

                }
                dataStatus.DataIsLoaded(barcodes);
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
