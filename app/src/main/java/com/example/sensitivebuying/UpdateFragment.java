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
import java.util.concurrent.TimeUnit;


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

    private ProgressBar progressBarUpdate;
    final Sensitive eggs = new Sensitive("ביצים","0");
    final Sensitive peants = new Sensitive("בוטנים","1");
    final Sensitive gluten = new Sensitive("גלוטן","2");
    final Sensitive nuts = new Sensitive("אגוזים","3");
    final Sensitive soya = new Sensitive("סויה","4");
    final Sensitive lactose = new Sensitive("לקטוז","5");
    final Sensitive sesame = new Sensitive("שומשום","6");
    final Sensitive pine_nut = new Sensitive("צנובר","7");
    final Sensitive sinapis = new Sensitive("חרדל","8");
    final Sensitive celery = new Sensitive("סלרי","9");

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

        progressBarUpdate = v.findViewById(R.id.pb_update_senstive);



        readData();


        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inProgress(true);


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

                if (pine_nutCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(pine_nut);
                } else {
                    userCheckBoxSensitive.remove(pine_nut);
                }
                if (sinapisCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(sinapis);
                } else {
                    userCheckBoxSensitive.remove(sinapis);
                }
                if (celeryCheckBox.isChecked()) {
                    userCheckBoxSensitive.add(celery);
                } else {
                    userCheckBoxSensitive.remove(celery);
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
            if (s.equals(eggs)) {
                eggsCheckBox.setChecked(true);
            }

            if (s.equals(peants)) {
                peantsCheckBox.setChecked(true);
            }

            if (s.equals(gluten)) {
                glutenCheckBox.setChecked(true);
            }

            if (s.equals(nuts)) {
                nutsCheckBox.setChecked(true);
            }

            if (s.equals(lactose)) {
                lactoseCheckBox.setChecked(true);
            }

            if (s.equals(soya)) {
                soyaCheckBox.setChecked(true);
            }

            if (s.equals(sesame)) {
                sesameCheckBox.setChecked(true);
            }

            if (s.equals(pine_nut)) {
            pine_nutCheckBox.setChecked(true);

            }

            if (s.equals(sinapis)) {
            sinapisCheckBox.setChecked(true);

            }
            if (s.equals(celery)) {
            celeryCheckBox.setChecked(true);

            }

    }

    private void unCheck (Sensitive s) {

        if (s.equals(eggs)) {
            eggsCheckBox.setChecked(false);
        }

        if (s.equals(peants)) {
            peantsCheckBox.setChecked(false);
        }

        if (s.equals(gluten)) {
            glutenCheckBox.setChecked(false);
        }

        if (s.equals(nuts)) {
            nutsCheckBox.setChecked(false);
        }

        if (s.equals(lactose)) {
            lactoseCheckBox.setChecked(false);
        }

        if (s.equals(soya)) {
            soyaCheckBox.setChecked(false);
        }

        if (s.equals(sesame)) {
            sesameCheckBox.setChecked(false);
        }

        if (s.equals(pine_nut)) {
            pine_nutCheckBox.setChecked(false);
        }

        if (s.equals(sinapis)) {
            sinapisCheckBox.setChecked(false);
        }

        if (s.equals(celery)) {
            celeryCheckBox.setChecked(false);
        }

    }


}
