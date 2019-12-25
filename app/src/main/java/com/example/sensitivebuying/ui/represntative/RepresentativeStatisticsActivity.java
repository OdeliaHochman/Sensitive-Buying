package com.example.sensitivebuying.ui.represntative;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sensitivebuying.R;

public class RepresentativeStatisticsActivity extends AppCompatActivity {

    final String activity = "RepresentativeStatisticsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_statistics);
    }
}
