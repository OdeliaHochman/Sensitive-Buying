package com.example.sensitivebuying;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    final String activity = " CustomerDetailsActivity";
    private Button btn_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_customer_details);

        btn_contact=(Button)findViewById(R.id.btn_contact_rep_customerDetails);
        btn_contact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_contact) {
            Intent intent =new Intent(CustomerDetailsActivity.this,RepresentativeContactUsActivity.class);
            startActivity(intent);
    }
}
}
