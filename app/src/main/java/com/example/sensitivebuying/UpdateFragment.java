package com.example.sensitivebuying;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


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
    ProgressBar progressBarUpdate;

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

        progressBarUpdate = v.findViewById(R.id.pb_update_senstive);

        final Sensitive eggs = new Sensitive(getString(R.string.eggs),"0");
        final Sensitive peants = new Sensitive(getString(R.string.peanuts),"1");
        final Sensitive gluten = new Sensitive(getString(R.string.gluten),"2");
        final Sensitive nuts = new Sensitive(getString(R.string.nuts),"3");
        final Sensitive soya = new Sensitive(getString(R.string.soya),"4");
        final Sensitive lactose = new Sensitive(getString(R.string.lactose),"5");
        final Sensitive sesame = new Sensitive(getString(R.string.sesame),"6");

        new FirebaseSenstiveUserHelper().readSensitive(new FirebaseSenstiveUserHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Sensitive> sensitives, ArrayList<String> keys) {

                if ( sensitives!=null) {
                    updateSensitive=new ArrayList<>(sensitives);

                    for (Sensitive s : updateSensitive) {
                        Log.d("debugInToggle",""+s);

                        if (s.equals(eggs)) {
                            eggsCheckBox.toggle();
                        }

                        if (s.equals(peants)) {
                            peantsCheckBox.toggle();
                        }

                        if (s.equals(gluten)) {
                            glutenCheckBox.toggle();
                        }

                        if (s.equals(nuts)) {
                            nutsCheckBox.toggle();
                        }

                        if (s.equals(lactose)) {
                            lactoseCheckBox.toggle();
                        }

                        if (s.equals(soya)) {
                            soyaCheckBox.toggle();
                        }

                        if (s.equals(sesame)) {
                            sesameCheckBox.toggle();
                        }
                    }

                }
                inProgress(false);


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
        }); inProgress(true);



        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  inProgress(false);


                ArrayList<Sensitive> userCheckBoxSensitive= new ArrayList<>();

                if (peantsCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(peants);
                } else {
                    userCheckBoxSensitive.remove(peants);
                }

                if (eggsCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(eggs);
                } else {
                    userCheckBoxSensitive.remove(eggs);
                }

                if (glutenCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(gluten);
                } else {
                    userCheckBoxSensitive.remove(gluten);
                }

                if (lactoseCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(lactose);
                } else {
                    userCheckBoxSensitive.remove(lactose);
                }

                if (nutsCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(nuts);
                } else {
                    userCheckBoxSensitive.remove(nuts);
                }

                if (soyaCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(soya);
                } else {
                    userCheckBoxSensitive.remove(soya);
                }

                if (sesameCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(sesame);
                } else {
                    userCheckBoxSensitive.remove(sesame);
                }

                ArrayList<Sensitive> needToDelete = new ArrayList<>(updateSensitive);
               needToDelete.removeAll(userCheckBoxSensitive);

               for (Sensitive del:needToDelete) {
    new FirebaseSenstiveUserHelper().deleteData(del.getKey(), new FirebaseSenstiveUserHelper.DataStatus() {
        @Override
        public void DataIsLoaded(ArrayList<Sensitive> sensitives, ArrayList<String> keys) {

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

                    for (Sensitive s:userCheckBoxSensitive ) {
                   new  FirebaseSenstiveUserHelper().addSensitive(s, new FirebaseSenstiveUserHelper.DataStatus() {
                       @Override
                       public void DataIsLoaded(ArrayList<Sensitive> sensitives, ArrayList<String> keys) {

                       }

                       @Override
                       public void DataIsInserted() {
                           inProgress(false);

                       }

                       @Override
                       public void DataIsUpdated() {

                       }

                       @Override
                       public void DataIsDeleted() {

                       }
                   });
                }
                userCheckBoxSensitive.clear();
            }
        });


        return v;

    }


    private void  inProgress ( boolean flag) {

        if(flag){

            progressBarUpdate.setVisibility(View.VISIBLE);
        }

        else {

            progressBarUpdate.setVisibility(View.GONE);
        }
    }

}