package com.example.sensitivebuying;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    final String activity = " CustomerDetailsActivity";
    private Button btn_contact;
    private ImageView shareImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_customer_details);

        btn_contact=(Button)findViewById(R.id.btn_contact_rep_customerDetails);
        btn_contact.setOnClickListener(this);

        shareImage=(ImageView)findViewById(R.id.shareimage);
//        shareImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Android Development Tutorials");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "www.technxt.net");
//                shareIntent.setType("text/plain");
//                startActivity(shareIntent);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if(v==btn_contact) {
            Intent intent =new Intent(CustomerDetailsActivity.this,RepresentativeContactUsActivity.class);
            startActivity(intent);
    }
}
}
