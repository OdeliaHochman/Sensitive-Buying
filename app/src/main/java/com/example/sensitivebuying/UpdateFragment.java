package com.example.sensitivebuying;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment  {

    private ArrayList<Sensitive> updateSensitive= new ArrayList<Sensitive>();
    private DatabaseReference usersReference;
    private Button ButtonSave;
    private FirebaseAuth firebaseAuth;
    private CustomerUser user;
    private View v;
    final String activity = "  UpdateFragment";
    CheckBox peantsCheckBox;
    CheckBox nutsCheckBox;
    CheckBox lactoseCheckBox;
    CheckBox glutenCheckBox;
    CheckBox eggsCheckBox;
    CheckBox sesameCheckBox;
    CheckBox soyaCheckBox;


    public UpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("debug",activity);
        v = inflater.inflate(R.layout.fragment_update, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        usersReference = FirebaseDatabase.getInstance().getReference("Users");

        ButtonSave = v.findViewById(R.id.btnsave_registersens_menu);
        peantsCheckBox=(CheckBox)v.findViewById(R.id.checkBox_peants_menu);
        nutsCheckBox=(CheckBox)v.findViewById(R.id.checkBox_nuts_menu);

        lactoseCheckBox=(CheckBox)v.findViewById(R.id.checkBox_lactose_menu);
        glutenCheckBox=(CheckBox)v.findViewById(R.id.checkBox_gluten_menu);
        eggsCheckBox=(CheckBox)v.findViewById(R.id.checkBox_eggs_menu);
        sesameCheckBox=(CheckBox)v.findViewById(R.id.checkBox_sesame_menu);
        soyaCheckBox=(CheckBox)v.findViewById(R.id.checkBox_soya_menu);


        usersReference .child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(CustomerUser.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("DbError", "loadPost:onCancelled", databaseError.toException());

                    }

                });

        final Sensitive eggs = new Sensitive(getString(R.string.eggs));
        final Sensitive peants = new Sensitive(getString(R.string.peanuts));
        final Sensitive gluten = new Sensitive(getString(R.string.gluten));
        final Sensitive nuts = new Sensitive(getString(R.string.nuts));
        final Sensitive soya = new Sensitive(getString(R.string.soya));
        final Sensitive lactose = new Sensitive(getString(R.string.lactose));
        final Sensitive sesame = new Sensitive(getString(R.string.sesame));

        if( user.getSensitiveList()!=null) {
            ArrayList<Sensitive> currSenstiveFireBase = user.getSensitiveList();
            for(Sensitive s: currSenstiveFireBase) {
                if (s==eggs){
                    eggsCheckBox.toggle();
                }

                if(s==peants){
                    peantsCheckBox.toggle();
                }

                if(s==gluten){
                    peantsCheckBox.toggle();
                }

                if (s==nuts) {
                    nutsCheckBox.toggle();
                }

                if (s==lactose){
                    lactoseCheckBox.toggle();
                }

                if (s==soya){
                    soyaCheckBox.toggle();
                }

                if (s==sesame){
                    sesameCheckBox.toggle();
                }
            }
        }


        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (peantsCheckBox.isChecked()) {
                    updateSensitive.add(peants);
                } else {
                    updateSensitive.remove(peants);
                }

                if (eggsCheckBox.isChecked()) {
                    updateSensitive.add(eggs);
                } else {
                    updateSensitive.remove(eggs);
                }

                if (glutenCheckBox.isChecked()) {
                    updateSensitive.add(gluten);
                } else {
                    updateSensitive.remove(gluten);
                }

                if (lactoseCheckBox.isChecked()) {
                    updateSensitive.add(lactose);
                } else {
                    updateSensitive.remove(lactose);
                }

                if (nutsCheckBox.isChecked()) {
                    updateSensitive.add(nuts);
                } else {
                    updateSensitive.remove(nuts);
                }

                if (soyaCheckBox.isChecked()) {
                    updateSensitive.add(soya);
                } else {
                    updateSensitive.remove(soya);
                }

                if (sesameCheckBox.isChecked()) {
                    updateSensitive.add(sesame);
                } else {
                    updateSensitive.remove(sesame);
                }



                user.setSensitiveList(updateSensitive);
                usersReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);

            }
        });


        return v;

    }

        }




