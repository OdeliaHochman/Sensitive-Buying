package com.example.sensitivebuying.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensitivebuying.R;
import com.example.sensitivebuying.ui.customer.HostNavigationActivity;
import com.example.sensitivebuying.ui.represntative.RepresentativeHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OpenActivity extends AppCompatActivity {

    final String activity = " OpenActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser autoUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        firebaseAuth = FirebaseAuth.getInstance();
        autoUser = firebaseAuth.getCurrentUser();
        setContentView(R.layout.activity_open);

        if(autoUser!=null ) {
            setUser();
        }

        else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(1500);
                            Intent intent = new Intent(OpenActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    private void setUser()
    {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("Users").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("rep").getValue(Boolean.class).equals(true)) {
                    startActivity(new Intent(OpenActivity.this, RepresentativeHomeActivity.class));
                    finish(); return;
                } else {
                    startActivity(new Intent(OpenActivity.this, HostNavigationActivity.class));
                    finish(); return;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("firebase", databaseError.getMessage());
            }
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);

    }
}
