package com.example.sensitivebuying;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SensitiveRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    final String activity = " SensitiveRegisterActivity";

    private ArrayList<Sensitive> updateSensitive= new ArrayList<Sensitive>();
    private DatabaseReference usersReference;
    private Button ButtonSave;
    private FirebaseAuth firebaseAuth;
    private CustomerUser user;
    private  TextView skipT;



    //    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debug",activity);
        super.onCreate(savedInstanceState);
        user = (CustomerUser) getIntent().getSerializableExtra("CustomerUser"); // recive obj from last activity
        firebaseAuth = FirebaseAuth.getInstance();
        usersReference = FirebaseDatabase.getInstance().getReference("Users");
        setContentView(R.layout.activity_sensitive_register);

        ButtonSave= findViewById(R.id.btnsave_registersens_registersens);
        ButtonSave.setOnClickListener(this);

        skipT = (TextView)findViewById(R.id.skip_registersens);
        skipT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==ButtonSave) {
            for (Sensitive s:updateSensitive ) {
                new  FirebaseSenstiveUserHelper().addSensitive(s, new FirebaseSenstiveUserHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(ArrayList<Sensitive> sensitives, ArrayList<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                        Toast.makeText(SensitiveRegisterActivity.this,"הרגישיות עודכנו",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
            updateSensitive.clear();
            Intent intent =new Intent(SensitiveRegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        if(v==skipT)
        {
            Intent intent =new Intent(SensitiveRegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        }

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox_eggs__registersens:
            {
                Sensitive s= new Sensitive(getString(R.string.eggs),"0");
                if (checked){
                    updateSensitive.add(s);}
                else {
                    Log.d("in egg ",activity);
                    updateSensitive.remove(s); }
                break;
            }
            case R.id.checkBox_gluten__registersens:
            {
                Sensitive s= new Sensitive(getString(R.string.gluten),"2");

                if (checked) {
                    updateSensitive.add(s); }
                else {
                    Log.d("in gluten ",activity);
                    updateSensitive.remove(s);
                }
                break;
            }
            case R.id.checkBox_lactose_registersens: {
                Sensitive s= new Sensitive(getString(R.string.lactose),"5");

                if (checked) {
                    updateSensitive.add(s); }
                else {
                    Log.d("in lactoz ",activity);
                    updateSensitive.remove(s);
                }
                break;
            }
            case R.id.checkBox_nuts_registersens: {
                Sensitive s= new Sensitive(getString(R.string.nuts),"3");

                if (checked) {
                    updateSensitive.add(s); }
                else {
                    Log.d("in agoz ",activity);
                    updateSensitive.remove(s);
                }
                break;
            }
            case R.id.checkBox_peants_registersens: {
                Sensitive s= new Sensitive(getString(R.string.peanuts),"1");

                if (checked) {
                    updateSensitive.add(s); }
                else {
                    Log.d("in botnim ",activity);
                    updateSensitive.remove(s);
                }
                break;
            }
            case R.id.checkBox_soya_registersens: {
                Sensitive s= new Sensitive(getString(R.string.soya),"4");

                if (checked) {
                    updateSensitive.add(s); }
                else {
                    Log.d("in soya ",activity);
                    updateSensitive.remove(s);
                }
                break;
            }
            case R.id.checkBox_sesame_registersens: {
                Sensitive s= new Sensitive(getString(R.string.sesame),"6");

                if (checked) {
                    updateSensitive.add(s); }
                 else {
                    Log.d("in somsom ",activity);
                    updateSensitive.remove(s);
                }
                break;
            }

            case R.id.checkBox_pine_nut_registersens: {
                Sensitive s= new Sensitive(getString(R.string.pine_nut),"7");

                if (checked) {
                    updateSensitive.add(s); }
                else {
                    Log.d("in pine_nut ",activity);
                    updateSensitive.remove(s);
                }
                break;
            }

            case R.id.checkBox_sinapis_registersens: {
                Sensitive s= new Sensitive(getString(R.string.sinapis),"8");

                if (checked) {
                    updateSensitive.add(s); }
                else {
                    Log.d("in sinapis ",activity);
                    updateSensitive.remove(s);
                }
                break;
            }

            case R.id.checkBox_celery_registersens: {
                Sensitive s= new Sensitive(getString(R.string.celery),"9");

                if (checked) {
                    updateSensitive.add(s); }
                else {
                    Log.d("in celery ",activity);
                    updateSensitive.remove(s);
                }
                break;
            }

        }
    }



}