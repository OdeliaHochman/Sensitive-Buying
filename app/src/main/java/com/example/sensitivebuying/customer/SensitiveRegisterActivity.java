package com.example.sensitivebuying.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sensitivebuying.dataObject.CustomerUser;
import com.example.sensitivebuying.firebaseHelper.FirebaseSenstiveUserHelper;
import com.example.sensitivebuying.R;
import com.example.sensitivebuying.dataObject.Sensitive;
import com.example.sensitivebuying.dataObject.SenstivieListFinal;
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
    private final SenstivieListFinal sensList = new SenstivieListFinal ();



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
                new FirebaseSenstiveUserHelper().addSensitive(s, new FirebaseSenstiveUserHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(ArrayList<Sensitive> sensitives, ArrayList<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                        Toast.makeText(SensitiveRegisterActivity.this,"הרגישויות עודכנו",Toast.LENGTH_LONG).show();

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

           moveToHost();
        }
        if(v==skipT)
        {
            moveToHost();

        }

    }
    private  void moveToHost () {
        Intent intent =new Intent(SensitiveRegisterActivity.this, HostNavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox_eggs__registersens:
            {
                if (checked){
                    updateSensitive.add(sensList.eggs);}
                else {
                    Log.d("in egg ",activity);
                    updateSensitive.remove(sensList.eggs); }
                break;
            }
            case R.id.checkBox_gluten__registersens:
            {

                if (checked) {
                    updateSensitive.add(sensList.gluten); }
                else {
                    Log.d("in gluten ",activity);
                    updateSensitive.remove(sensList.gluten);
                }
                break;
            }
            case R.id.checkBox_lactose_registersens: {

                if (checked) {
                    updateSensitive.add(sensList.lactose); }
                else {
                    Log.d("in lactoz ",activity);
                    updateSensitive.remove(sensList.lactose);
                }
                break;
            }
            case R.id.checkBox_nuts_registersens: {

                if (checked) {
                    updateSensitive.add(sensList.nuts); }
                else {
                    Log.d("in agoz ",activity);
                    updateSensitive.remove(sensList.nuts);
                }
                break;
            }
            case R.id.checkBox_peants_registersens: {

                if (checked) {
                    updateSensitive.add(sensList.peants); }
                else {
                    Log.d("in botnim ",activity);
                    updateSensitive.remove(sensList.peants);
                }
                break;
            }
            case R.id.checkBox_soya_registersens: {

                if (checked) {
                    updateSensitive.add(sensList.soya); }
                else {
                    Log.d("in soya ",activity);
                    updateSensitive.remove(sensList.soya);
                }
                break;
            }
            case R.id.checkBox_sesame_registersens: {

                if (checked) {
                    updateSensitive.add(sensList.sesame); }
                 else {
                    Log.d("in somsom ",activity);
                    updateSensitive.remove(sensList.sesame);
                }
                break;
            }

            case R.id.checkBox_pine_nut_registersens: {

                if (checked) {
                    updateSensitive.add(sensList.pine_nut); }
                else {
                    Log.d("in pine_nut ",activity);
                    updateSensitive.remove(sensList.pine_nut);
                }
                break;
            }

            case R.id.checkBox_sinapis_registersens: {

                if (checked) {
                    updateSensitive.add(sensList.sinapis); }
                else {
                    Log.d("in sinapis ",activity);
                    updateSensitive.remove(sensList.sinapis);
                }
                break;
            }

            case R.id.checkBox_celery_registersens: {

                if (checked) {
                    updateSensitive.add(sensList.celery); }
                else {
                    Log.d("in celery ",activity);
                    updateSensitive.remove(sensList.celery);
                }
                break;
            }

        }
    }



}