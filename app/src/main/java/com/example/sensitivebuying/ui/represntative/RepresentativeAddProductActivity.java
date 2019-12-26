package com.example.sensitivebuying.ui.represntative;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensitivebuying.AddProduct_SensitivesCheckBoxSpinner;
import com.example.sensitivebuying.dataObject.RepresentativeUser;
import com.example.sensitivebuying.dataObject.User;
import com.example.sensitivebuying.firebaseHelper.FirebaseCompaniesHelper;
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsHelper;
import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.R;
import com.example.sensitivebuying.dataObject.Sensitive;
import com.example.sensitivebuying.Sensitive_Checkbox;
import com.example.sensitivebuying.firebaseHelper.FirebaseUserHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeAddProductActivity extends AppCompatActivity {

    private Button btnSave;
    private EditText codebar;
    private EditText nameCompany;
    private EditText infoProduct;
    private EditText nameProduct;
    private Spinner spinner;
    private EditText urlImage;
    private EditText weightSen;
    private FirebaseDatabase firebaseDatabase;
    private ProgressBar mProgressBar;
    private boolean isAdd=true; // if this add new or edit

    final String activity = " RepresentativeAddProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug", activity);
        setContentView(R.layout.activity_representative_add_product);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final String barcode = getIntent().getStringExtra("barcode");
        codebar = findViewById(R.id.barcode_editTxt);

        nameCompany = findViewById(R.id.company_editTxt);
        infoProduct = findViewById(R.id.information_editTxt);
        nameProduct = findViewById(R.id.nameProduct_editTxt);
        urlImage = findViewById(R.id.image_editTxt);
        weightSen = findViewById(R.id.weight_editTxt);
        btnSave = findViewById(R.id.save_button);
        mProgressBar= findViewById(R.id.progressBar_add_product);
        setCompanyName();

        if (barcode != null) { // if you get from update page
            isAdd=false;
            btnSave.setText("עדכן"); // change the txt button
            setDetails(barcode);
            codebar.setFocusable(false); // unable change the barcode

        }



        final String[] select_qualification = {"    בחר רגישויות  ", "ביצים", "בוטנים", "גלוטן", "אגוזים", "סויה", "לקטוז", "שומשום", "צנובר" , "חרדל" ,"סלרי"};
        spinner = (Spinner) findViewById(R.id.sensitives_spinner);

        final ArrayList<Sensitive> listOfSensitive = new ArrayList<>();
        final ArrayList<Sensitive_Checkbox> listbox=new ArrayList<>();
        final ArrayList<Sensitive> listOfSensitiveTrue = new ArrayList<>();


        for (int i = 0; i < select_qualification.length; i++) {
            final Sensitive sensitive = new Sensitive();
            final Sensitive_Checkbox senbox= new Sensitive_Checkbox();
            sensitive.setSensitiveType(select_qualification[i]);
            sensitive.setKey(Integer.toString(i-1));
            senbox.setSensitiveTypebox(select_qualification[i]);
            listOfSensitive.add(sensitive);
            listbox.add(senbox);


            AddProduct_SensitivesCheckBoxSpinner addProductSensitivesSpinner = new AddProduct_SensitivesCheckBoxSpinner(this, 0, listbox);
            spinner.setAdapter(addProductSensitivesSpinner);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEmpty()) // if one of the text view is empty
                        return;

                    for (int j = 0; j < listbox.size(); j++) {
                        if (listbox.get(j).getSelectedbox() == true) {
                            listOfSensitiveTrue.add(listOfSensitive.get(j));
                        }
                    }

                    final Product product = new Product();
                    product.setBarcode(codebar.getText().toString());
                    product.setCompanyName(nameCompany.getText().toString());
                    product.setProductDescription(infoProduct.getText().toString());
                    product.setProductName(nameProduct.getText().toString());
                    product.setSensitiveList(listOfSensitiveTrue);
                    product.setUrlImage(urlImage.getText().toString());
                    product.setWeightAndType(weightSen.getText().toString());
                    if (isAdd) {

                    new FirebaseCompaniesHelper().addProductOfCompanies(product, new FirebaseCompaniesHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<String> keys) {

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
                        new FirebaseProductsHelper().addProduct(product, new FirebaseProductsHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Product> productsList, List<String> keys) {
                            }

                            @Override
                            public void DataIsInserted() {

                                Toast.makeText(RepresentativeAddProductActivity.this, "המוצר התווסף בהצלחה", Toast.LENGTH_LONG).show();
                                finish();
                                return;

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    } else { // update
                        String barcode_new = codebar.getText().toString();
                        new FirebaseProductsHelper().updateProduct(barcode_new, product, new FirebaseProductsHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Product> productsList, List<String> keys) {

                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {
                                Toast.makeText(RepresentativeAddProductActivity.this, "המוצר התעדכן בהצלחה", Toast.LENGTH_LONG).show();
                                finish();
                                return;
                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }
                }
            });
        }

    }

    private void setDetails (String barcode) {

        inProgress(true);

        DatabaseReference reference = firebaseDatabase.getReference("Products").child(barcode);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product p = dataSnapshot.getValue(Product.class);

                codebar.setText(p.getBarcode());
                nameCompany.setText(p.getCompanyName());
                infoProduct.setText(p.getProductDescription());
                nameProduct.setText(p.getProductName());
                urlImage.setText(p.getUrlImage());
                weightSen.setText(p.getWeightAndType());
                inProgress(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void  inProgress ( boolean flag) {

        if(flag){

            mProgressBar.setVisibility(View.VISIBLE);
            codebar.setEnabled(false);
            nameCompany.setEnabled(false);
            infoProduct.setEnabled(false);
            nameProduct.setEnabled(false);
            urlImage.setEnabled(false);
            weightSen.setEnabled(false);
        }

        else {

            mProgressBar.setVisibility(View.GONE);
            codebar.setEnabled(true);
            nameCompany.setEnabled(true);
            infoProduct.setEnabled(true);
            nameProduct.setEnabled(true);
            urlImage.setEnabled(true);
            weightSen.setEnabled(true);        }
    }

    private  boolean isEmpty () {
        if (TextUtils.isEmpty(codebar.getText().toString())) {
            Toast.makeText(this, "לא הוכנס ברקוד", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(nameCompany.getText().toString())) {
            Toast.makeText(this, "לא הוכנס שם חברה", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(infoProduct.getText().toString())) {
            Toast.makeText(this, "לא הוכנס פרטי מוצר", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(nameProduct.getText().toString())) {
            Toast.makeText(this, "לא הוכנס שם מוצר", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(urlImage.getText().toString())) {
            Toast.makeText(this, "לא הוכנסה תמונה", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(weightSen.getText().toString())) {
            Toast.makeText(this, "לא הוכנס משקל", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private void setCompanyName () {
        new FirebaseUserHelper().readUser(new FirebaseUserHelper.DataStatusUser() {
            @Override
            public void DataIsLoaded(User userHelper, String key) {
                RepresentativeUser repUser = (RepresentativeUser) userHelper;
                String companyName = repUser.getCompanyName();
                nameCompany.setText(companyName);
                nameCompany.setFocusable(false); // unable change the company name


            }
        });
    }
}
