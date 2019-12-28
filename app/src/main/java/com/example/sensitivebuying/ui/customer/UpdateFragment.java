package com.example.sensitivebuying.ui.customer;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.sensitivebuying.dataObject.CustomerUser;
import com.example.sensitivebuying.firebaseHelper.FirebaseSenstiveUserHelper;
import com.example.sensitivebuying.R;
import com.example.sensitivebuying.dataObject.Sensitive;
import com.example.sensitivebuying.dataObject.SenstivieListFinal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private CheckBox peantsCheckBox,nutsCheckBox,lactoseCheckBox,glutenCheckBox;
    private CheckBox eggsCheckBox,sesameCheckBox,soyaCheckBox;
    private CheckBox pine_nutCheckBox,sinapisCheckBox,celeryCheckBox ;
    private CheckBox milkCheckBox,tonsilsCheckBox;

    private ProgressBar progressBarUpdate;

    final SenstivieListFinal senList= new SenstivieListFinal();

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
        pine_nutCheckBox=(CheckBox)v.findViewById(R.id.checkBox_pine_nut_menu);
        sinapisCheckBox=(CheckBox)v.findViewById(R.id.checkBox_sinapis_menu);
        celeryCheckBox=(CheckBox)v.findViewById(R.id.checkBox_celery_menu);
        milkCheckBox=(CheckBox) v.findViewById(R.id.checkBox_milk_menu);
        tonsilsCheckBox=(CheckBox) v.findViewById(R.id.checkBox_tonsils_menu);



        progressBarUpdate = v.findViewById(R.id.pb_update_senstive);

        readData();


        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inProgress(true);


                ArrayList<Sensitive> userCheckBoxSensitive= new ArrayList<>();

                if (peantsCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.peants);
                } else {
                    userCheckBoxSensitive.remove(senList.peants);
                }

                if (eggsCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.eggs);
                } else {
                    userCheckBoxSensitive.remove(senList.eggs);
                }

                if (glutenCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.gluten);
                } else {
                    userCheckBoxSensitive.remove(senList.gluten);
                }

                if (lactoseCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.lactose);
                } else {
                    userCheckBoxSensitive.remove(senList.lactose);
                }

                if (nutsCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.nuts);
                } else {
                    userCheckBoxSensitive.remove(senList.nuts);
                }

                if (soyaCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.soya);
                } else {
                    userCheckBoxSensitive.remove(senList.soya);
                }

                if (sesameCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.sesame);
                } else {
                    userCheckBoxSensitive.remove(senList.sesame);
                }

                if (pine_nutCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.pine_nut);
                } else {
                    userCheckBoxSensitive.remove(senList.pine_nut);
                }
                if (sinapisCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.sinapis);
                } else {
                    userCheckBoxSensitive.remove(senList.sinapis);
                }
                if (celeryCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.celery);
                } else {
                    userCheckBoxSensitive.remove(senList.celery);
                }
                if (milkCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.milk);
                } else {
                    userCheckBoxSensitive.remove(senList.milk);
                }
                if (tonsilsCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(senList.tonsils);
                } else {
                    userCheckBoxSensitive.remove(senList.tonsils);
                }

                deleteUnChecked(userCheckBoxSensitive);
                addSensitive(userCheckBoxSensitive);

                inProgress(false);
                userCheckBoxSensitive.clear();
            }

        });



        return v;

    }


private void readData() {
    inProgress(true);

    new FirebaseSenstiveUserHelper().readSensitive(new FirebaseSenstiveUserHelper.DataStatus() {
        @Override
        public void DataIsLoaded(ArrayList<Sensitive> sensitives, ArrayList<String> keys) {
            updateSensitive = new ArrayList<>(sensitives);
            for (Sensitive s: updateSensitive){
                check(s);
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
}

private void deleteUnChecked (ArrayList<Sensitive> userCheckBoxSensitive) {

    ArrayList<Sensitive> needToDelete = new ArrayList<>(updateSensitive);
    needToDelete.removeAll(userCheckBoxSensitive);

    for (final Sensitive del:needToDelete) {
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
                unCheck(del);
            }
        });
    }


}

private void addSensitive( ArrayList<Sensitive> userCheckBoxSensitive)  {
    for (final Sensitive s:userCheckBoxSensitive ) {
        new  FirebaseSenstiveUserHelper().addSensitive(s, new FirebaseSenstiveUserHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Sensitive> sensitives, ArrayList<String> keys) {

            }

            @Override
            public void DataIsInserted() {
                check(s);

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

    private void  inProgress ( boolean flag) {

        if(flag){

            progressBarUpdate.setVisibility(View.VISIBLE);
        }

        else {

            progressBarUpdate.setVisibility(View.GONE);
        }
    }

    private void check (Sensitive s) {
            if (s.equals(senList.eggs)) {
                eggsCheckBox.setChecked(true);
            }

            if (s.equals(senList.peants)) {
                peantsCheckBox.setChecked(true);
            }

            if (s.equals(senList.gluten)) {
                glutenCheckBox.setChecked(true);
            }

            if (s.equals(senList.nuts)) {
                nutsCheckBox.setChecked(true);
            }

            if (s.equals(senList.lactose)) {
                lactoseCheckBox.setChecked(true);
            }

            if (s.equals(senList.soya)) {
                soyaCheckBox.setChecked(true);
            }

            if (s.equals(senList.sesame)) {
                sesameCheckBox.setChecked(true);
            }

            if (s.equals(senList.pine_nut)) {
            pine_nutCheckBox.setChecked(true);

            }

            if (s.equals(senList.sinapis)) {
            sinapisCheckBox.setChecked(true);

            }
            if (s.equals(senList.celery)) {
            celeryCheckBox.setChecked(true);

            }
            if (s.equals(senList.milk)) {
                milkCheckBox.setChecked(true);

            }
            if (s.equals(senList.tonsils)) {
                tonsilsCheckBox.setChecked(true);

            }


    }

    private void unCheck (Sensitive s) {

        if (s.equals(senList.eggs)) {
            eggsCheckBox.setChecked(false);
        }

        if (s.equals(senList.peants)) {
            peantsCheckBox.setChecked(false);
        }

        if (s.equals(senList.gluten)) {
            glutenCheckBox.setChecked(false);
        }

        if (s.equals(senList.nuts)) {
            nutsCheckBox.setChecked(false);
        }

        if (s.equals(senList.lactose)) {
            lactoseCheckBox.setChecked(false);
        }

        if (s.equals(senList.soya)) {
            soyaCheckBox.setChecked(false);
        }

        if (s.equals(senList.sesame)) {
            sesameCheckBox.setChecked(false);
        }

        if (s.equals(senList.pine_nut)) {
            pine_nutCheckBox.setChecked(false);
        }

        if (s.equals(senList.sinapis)) {
            sinapisCheckBox.setChecked(false);
        }

        if (s.equals(senList.celery)) {
            celeryCheckBox.setChecked(false);
        }

        if (s.equals(senList.milk)) {
            milkCheckBox.setChecked(false);
        }

        if (s.equals(senList.tonsils)) {
            tonsilsCheckBox.setChecked(false);
        }

    }


}
