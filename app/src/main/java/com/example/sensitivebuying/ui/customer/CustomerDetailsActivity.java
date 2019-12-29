package com.example.sensitivebuying.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    final String activity = " CustomerDetailsActivity";
    private Button btn_contact;
    private ImageView shareImage;
    private ToggleButton toggleButton;
    private FirebaseDatabase firebaseDatabase;
    private TextView productName,companyName,weight, productDetails,barcode,sensitiveStr;
    private ImageView productImage;
    private String barcodeS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_customer_details);
        firebaseDatabase = FirebaseDatabase.getInstance();
        barcodeS = getIntent().getStringExtra("barcode");
        btn_contact=(Button)findViewById(R.id.btn_contact_rep_customerDetails);
        btn_contact.setOnClickListener(this);

        productName = (TextView) findViewById(R.id.product_name_Adetails_customer);
        companyName = (TextView) findViewById(R.id.company_name_Adetails_customer);
        weight = (TextView) findViewById(R.id.product_weight_name_Adetails_customer);
        productDetails = (TextView) findViewById(R.id.product_details_name_Adetails_customer);
        barcode = (TextView) findViewById(R.id.barcode_Adetails_customer);
        sensitiveStr=(TextView) findViewById(R.id.sensitiveList_details_customer);
        productImage = (ImageView)findViewById(R.id.product_image_Adetails_customer);

        new FirebaseProductsHelper().readOneProduct(barcodeS, new FirebaseProductsHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Product> productsList, List<String> keys) {

            }

            @Override
            public void ProductDataLoaded(Product product)
            {

                companyName.setText(product.getProductName());
                productName.setText(product.getProductName());
                barcode.setText(product.getBarcode());
                weight.setText(product.getWeightAndType());
                productDetails.setText(product.getProductDescription());
                Picasso.get().load(product.getUrlImage()).into(productImage);
                String [] strSensitive=new String[product.getSensitiveList().size()];
                if(product.getSensitiveList()==null)
                {
                    String str="אין רגישיות";
                    sensitiveStr.setText(str);
                }
                else {
                    for (int i = 0; i < product.getSensitiveList().size(); i++) {
                        strSensitive[i] = product.getSensitiveList().get(i).getSensitiveType();
                    }
                    String sensitives = TextUtils.join(",", strSensitive);
                    sensitiveStr.setText(sensitives);

                }

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
        });



        shareImage=(ImageView)findViewById(R.id.shareimage);
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "www.technxt.net");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });

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
            Intent intent =new Intent(CustomerDetailsActivity.this, RepresentativeContactActivity.class);
            startActivity(intent);
        }
    }

}



