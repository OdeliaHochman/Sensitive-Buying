package com.example.sensitivebuying.ui.represntative;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensitivebuying.dataObject.Sensitive;
import com.example.sensitivebuying.firebaseHelper.FirebaseCompaniesHelper;
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsBySensitiveHelper;
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsHelper;
import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeProductDetailsActivity extends AppCompatActivity {

    private TextView productName,companyName,weight, productDetails,barcode,sensitiveStr;
    private ImageView productImage;
    private Button btn_contact;
    private Button btnUpdate,btnDelete;
    final String activity = " RepresentativeProductDetailsActivity";
    private String barcodeS;
    private FirebaseDatabase firebaseDatabase;
    private Product product;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_product_details);
        firebaseDatabase = FirebaseDatabase.getInstance();
        barcodeS = getIntent().getStringExtra("barcode"); // get barcode from listview
        companyName = (TextView) findViewById(R.id.company_name_Adetails);
        productName = (TextView) findViewById(R.id.product_name_Adetails);
        barcode= (TextView) findViewById(R.id.barcode_Adetails);
        weight = (TextView) findViewById(R.id.product_weight_name_Adetails);
        sensitiveStr=(TextView) findViewById(R.id.sensitiveList_details);
        productImage = (ImageView)findViewById(R.id.product_image_Adetails);
        productDetails = (TextView) findViewById(R.id.product_details_name_Adetails);


        new FirebaseProductsHelper().readOneProduct(barcodeS, new FirebaseProductsHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Product> productsList, List<String> keys) {

            }

            @Override
            public void ProductDataLoaded(Product p)
            {
                product=p;
                companyName.setText(product.getCompanyName());
                productName.setText(product.getProductName());
                barcode.setText(product.getBarcode());
                weight.setText(product.getWeightAndType());
                productDetails.setText(product.getProductDescription());
                Picasso.get().load(product.getUrlImage()).into(productImage);
                if(product.getSensitiveList()==null)
                {
                    String str="אין רגישיות";
                    sensitiveStr.setText(str);
                }
                else {
                    String [] strSensitive=new String[product.getSensitiveList().size()];
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


        btnUpdate = (Button)findViewById(R.id.btnUpdateProDet);
        btnDelete = (Button)findViewById(R.id.btnDeleteProDet);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RepresentativeProductDetailsActivity.this, RepresentativeUpdateProductActivity.class);
                intent.putExtra("barcode",barcodeS);
                startActivity(intent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(RepresentativeProductDetailsActivity.this);
                builder1.setMessage("האם אתה בטוח שברצונך למחוק את המוצר ? ");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "כן",
                        new DialogInterface.OnClickListener() {
                            public void onClick( final DialogInterface dialog, int id) {
                                // delete from product tree
                                new FirebaseProductsHelper().deleteProduct(barcodeS, new FirebaseProductsHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<Product> productsList, List<String> keys) {

                                    }

                                    @Override
                                    public void ProductDataLoaded(Product product) {

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

                                new FirebaseCompaniesHelper().deleteProduct(product, new FirebaseCompaniesHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<String> barcodesList) {

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

                                // delete from senstive tree

                                new FirebaseProductsBySensitiveHelper().deleteProduct(product, new FirebaseProductsBySensitiveHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(ArrayList<String> barcodes) {

                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {

                                    }

                                    @Override
                                    public void DataIsDeleted() {
                                        dialog.cancel();
                                        Toast.makeText(RepresentativeProductDetailsActivity.this,"המוצר הוסר בהצלחה" , Toast.LENGTH_LONG).show();
                                        finish();return;

                                    }
                                });

                            }
                        });

                builder1.setNegativeButton(
                        "לא",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }


        });

    }

}
