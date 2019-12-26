package com.example.sensitivebuying.ui.customer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sensitivebuying.R;
import com.example.sensitivebuying.ui.represntative.RepresentativeContactUsActivity;

public class CustomerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    final String activity = " CustomerDetailsActivity";
    private Button btn_contact;
    private ImageView shareImage;
    private ToggleButton toggleButton;


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

        toggleButton = (ToggleButton) findViewById(R.id.myToggleButton);
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.img_star_yellow));
                else
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==btn_contact) {
            Intent intent =new Intent(CustomerDetailsActivity.this, RepresentativeContactUsActivity.class);
            startActivity(intent);
    }
}


}
