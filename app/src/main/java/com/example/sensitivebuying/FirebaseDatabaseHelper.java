package com.example.sensitivebuying;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper implements Serializable {

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

    public FirebaseDatabaseHelper()
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


}
