package com.example.sensitivebuying;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RepresentativeProductDetailsActivity extends AppCompatActivity {

    private TextView productName,companyName,weight,productDetalis,barcode;
    private ImageView productImage;
    private Button btnUpdate;
    final String activity = " RepresentativeProductDetailsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_product_details);

        productName = (TextView) findViewById(R.id.product_name_Adetails);
        companyName = (TextView) findViewById(R.id.company_name_Adetails);
        weight = (TextView) findViewById(R.id.product_weight_name_Adetails);
        productDetalis = (TextView) findViewById(R.id.product_details_name_Adetails);
        barcode = (TextView) findViewById(R.id.barcode_Adetails);

        productImage = (ImageView)findViewById(R.id.product_image_Adetails);
        btnUpdate = (Button)findViewById(R.id.btnUpdateProDet);

    }
}
