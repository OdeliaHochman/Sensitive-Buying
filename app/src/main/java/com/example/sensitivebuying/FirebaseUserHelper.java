package com.example.sensitivebuying;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseUserHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    String userKey;
    private  User user;

    public interface DataStatusUser
    {
        void DataIsLoaded( User userHelper , String key);

    }

    public FirebaseUserHelper()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        userKey = firebaseAuth.getCurrentUser().getUid();
        mReference=mDatabase.getReference("Users").child(userKey);
    }


    public void readUser (final FirebaseUserHelper.DataStatusUser dataStatus)
    {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user =  dataSnapshot.getValue(User.class);
                if(user.getRep()) {
                RepresentativeUser rep = dataSnapshot.getValue(RepresentativeUser.class);
                    dataStatus.DataIsLoaded(rep,userKey);
                }
                else {
                    CustomerUser cus  = dataSnapshot.getValue(CustomerUser.class);
                    dataStatus.DataIsLoaded(cus,userKey);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
