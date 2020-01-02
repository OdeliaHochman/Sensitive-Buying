package com.example.sensitivebuying.ui.customer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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
import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.dataObject.Sensitive;
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsHelper;
import com.example.sensitivebuying.firebaseHelper.FirebaseSenstiveUserHelper;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    private ArrayList<Sensitive> userSen = new ArrayList<>();
    private Product product;



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
            public void ProductDataLoaded(Product p)
            {
                product =p;
                companyName.setText(p.getCompanyName());
                productName.setText(p.getProductName());
                barcode.setText(p.getBarcode());
                weight.setText(p.getWeightAndType());
                productDetails.setText(p.getProductDescription());
                Picasso.get().load(p.getUrlImage()).into(productImage);
                if(p.getSensitiveList()==null)
                {
                    String str="אין רגישיות";
                    sensitiveStr.setText(str);
                }
                else {
                    new FirebaseSenstiveUserHelper().readSensitive(new FirebaseSenstiveUserHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(ArrayList<Sensitive> sensitives, ArrayList<String> keys) {
                            userSen=sensitives;

                            String [] strSensitive=new String[product.getSensitiveList().size()];
                            int [] sizeOfBegining=new int[product.getSensitiveList().size()];
                            int sizeOfCurrSen=0;
                            for (int i = 0; i < product.getSensitiveList().size(); i++) {

                                sizeOfBegining[i]=sizeOfCurrSen;
                                sizeOfCurrSen+= product.getSensitiveList().get(i).getSensitiveType().length()+1;
                                strSensitive[i] = product.getSensitiveList().get(i).getSensitiveType();
                            }
                            String sensitivesTxt = TextUtils.join(",", strSensitive);
                            SpannableString ss = new SpannableString(sensitivesTxt);
                            ForegroundColorSpan fcsRed ;


                            for (int i = 0; i < product.getSensitiveList().size(); i++) {
                                if ( userSen.contains( product.getSensitiveList().get(i)) ) {
                                    fcsRed =  new ForegroundColorSpan(Color.RED);
                                    if ((i + 1) == product.getSensitiveList().size()) // end
                                        ss.setSpan(fcsRed, sizeOfBegining[i], sensitivesTxt.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    else
                                        ss.setSpan(fcsRed, sizeOfBegining[i], sizeOfBegining[i + 1] - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                }
                            }

                            sensitiveStr.setText(ss);

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
                String title_sharing = "המוצר שותף דרך אפליקציית Sensitive Buying"+"\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT,title_sharing+ product.toString());
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



