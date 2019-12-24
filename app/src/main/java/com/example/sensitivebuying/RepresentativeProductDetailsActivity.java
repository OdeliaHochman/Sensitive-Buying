package com.example.sensitivebuying;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RepresentativeProductDetailsActivity extends AppCompatActivity {

    private TextView productName,companyName,weight,productDetalis,barcode;
    private ImageView productImage;
    private Button btnUpdate,btnDelete;
    final String activity = " RepresentativeProductDetailsActivity";
    private String productNameS,companyNameS,weightS,productDetalisS,barcodeS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_product_details);

        productNameS = getIntent().getStringExtra("product name");
        companyNameS = getIntent().getStringExtra("company name");
        weightS = getIntent().getStringExtra("weight");
        barcodeS = getIntent().getStringExtra("barcode");
        productDetalisS = getIntent().getStringExtra("product detalis");

        productName = (TextView) findViewById(R.id.product_name_Adetails);
        productName.setText(productNameS);
        companyName = (TextView) findViewById(R.id.company_name_Adetails);
        companyName.setText(companyNameS);
        weight = (TextView) findViewById(R.id.product_weight_name_Adetails);
        weight.setText(weightS);
        productDetalis = (TextView) findViewById(R.id.product_details_name_Adetails);
        productDetalis.setText(productDetalisS);
        barcode = (TextView) findViewById(R.id.barcode_Adetails);
        barcode.setText(barcodeS);

        productImage = (ImageView)findViewById(R.id.product_image_Adetails);
        btnUpdate = (Button)findViewById(R.id.btnUpdateProDet);
        btnDelete = (Button)findViewById(R.id.btnDeleteProDet);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                product.setBarcode(barcode.getText().toString());
                product.setCompanyName(companyName.getText().toString());
                product.setProductDescription(productDetalis.getText().toString());
                product.setProductName(productName.getText().toString());
                //   product.setSensitiveList(sensitives.getText().toString());
               // product.setUrlImage(urlImage.getText().toString());
                product.setWeightAndType(weight.getText().toString());

                new FirebaseProductsHelper().updateProduct(barcodeS, product, new FirebaseProductsHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Product> productsList, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(RepresentativeProductDetailsActivity.this,"המוצר התעדכן בהצלחה" , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseProductsHelper().deleteProduct(barcodeS, new FirebaseProductsHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Product> productsList, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(RepresentativeProductDetailsActivity.this,"המוצר הוסר בהצלחה" , Toast.LENGTH_LONG).show();
                        finish();return;
                    }
                });
            }
        });

    }

}
