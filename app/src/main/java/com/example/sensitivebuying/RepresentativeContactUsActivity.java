package com.example.sensitivebuying;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class RepresentativeContactUsActivity extends AppCompatActivity {


    final String activity = "RepresentativeContactUsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_contact_us);
    }
}
