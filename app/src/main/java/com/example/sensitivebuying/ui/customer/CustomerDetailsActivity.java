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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sensitivebuying.R;
import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.ui.represntative.RepresentativeContactUsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CustomerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    final String activity = " CustomerDetailsActivity";
    private Button btn_contact;
    private ImageView shareImage;
    private ToggleButton toggleButton;
    private FirebaseDatabase firebaseDatabase;
    private TextView productName,companyName,weight, productDetails,barcode;
    private ImageView productImage;
    private String productNameS,companyNameS,weightS,barcodeS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_customer_details);
        firebaseDatabase = FirebaseDatabase.getInstance();

        btn_contact=(Button)findViewById(R.id.btn_contact_rep_customerDetails);
        btn_contact.setOnClickListener(this);

        productNameS = getIntent().getStringExtra("product name");
        companyNameS = getIntent().getStringExtra("company name");
        weightS = getIntent().getStringExtra("weight");
        barcodeS = getIntent().getStringExtra("barcode");

        productName = (TextView) findViewById(R.id.product_name_Adetails_customer);
        productName.setText(productNameS);
        companyName = (TextView) findViewById(R.id.company_name_Adetails_customer);
        companyName.setText(companyNameS);
        weight = (TextView) findViewById(R.id.product_weight_name_Adetails_customer);
        weight.setText(weightS);
        productDetails = (TextView) findViewById(R.id.product_details_name_Adetails_customer);
        barcode = (TextView) findViewById(R.id.barcode_Adetails_customer);
        barcode.setText(barcodeS);

        productImage = (ImageView)findViewById(R.id.product_image_Adetails_customer);

        setDetails(barcodeS);

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


    private void setDetails (String barcode) {

        DatabaseReference reference = firebaseDatabase.getReference("Products").child(barcode);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product p = dataSnapshot.getValue(Product.class);

                productDetails.setText(p.getProductDescription());
                Picasso.get().load(p.getUrlImage()).into(productImage);
                //sensitivelist
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
