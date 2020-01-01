package com.example.sensitivebuying.firebaseHelper;




        import android.util.Log;

        import androidx.annotation.NonNull;

        import com.example.sensitivebuying.dataObject.Sensitive;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;

public class FirebaseSenstiveUserHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    ArrayList<Sensitive> sensitives = new ArrayList<>();
    String userKey;

    public interface DataStatus
    {
        void DataIsLoaded(ArrayList<Sensitive> sensitives , ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseSenstiveUserHelper()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        userKey = firebaseAuth.getCurrentUser().getUid();
        mReference=mDatabase.getReference("Users").child(userKey).child("sensitiveList");
        Log.d("FirebaseUserHelper",""+userKey );
    }


    public void readSensitive (final DataStatus dataStatus)
    {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                sensitives.clear();
                ArrayList<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    Sensitive sensitive = keyNode.getValue(Sensitive.class);
                    sensitives.add(sensitive);
                }
                dataStatus.DataIsLoaded(sensitives,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    public void addSensitive (Sensitive sensitive,final DataStatus dataStatus ) {
        String key = sensitive.getsensitiveKey();
        mReference.child(key).setValue(sensitive).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void updateUser (String key,Sensitive customerUser, final DataStatus dataStatus) {

            mReference.child(key).setValue(customerUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dataStatus.DataIsUpdated();
                }
            });
    }

    public void deleteData (String key, final DataStatus dataStatus){
        mReference.child(key).setValue(null).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}
