package com.example.sensitivebuying;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FirebaseProductsHelper implements Serializable {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Product> productsList = new ArrayList<>();

    public interface DataStatus
    {
        void DataIsLoaded(List<Product> productsList , List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseProductsHelper()
    {

        mDatabase = FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference("Products");
    }


    public void readProducts(final DataStatus dataStatus)
    {
        mReference.addValueEventListener(new ValueEventListener() {
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

    public void addProduct(Product product , final DataStatus dataStatus)
    {
        String  barcode = product.getBarcode();
        mReference.child(barcode).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }
    public void updateProduct(String key,Product product,final DataStatus dataStatus)
    {
        mReference.child(key).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteProduct(String key , final DataStatus dataStatus)
    {
        mReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }





}
