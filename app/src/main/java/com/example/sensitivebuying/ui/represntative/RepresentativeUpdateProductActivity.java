package com.example.sensitivebuying.ui.represntative;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsBySensitiveHelper;
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
import java.util.concurrent.Semaphore;

public class RepresentativeUpdateProductActivity extends AppCompatActivity {

    private Button btnUpdate;
    private EditText codebar;
    private EditText nameCompany;
    private EditText infoProduct;
    private EditText nameProduct;
    private Spinner spinner;
    private Spinner spinnerUpdate;
    private EditText urlImage;
    private EditText weightSen;
    private FirebaseDatabase firebaseDatabase;
    private ProgressBar mProgressBar;
    private boolean isAdd=true; // if this add new or edit
    Product product;
    Sensitive_Checkbox senbox;
    Sensitive_Checkbox senUpdatebox;
    ArrayList<Sensitive_Checkbox> listboxUpdate;
    ArrayList<Sensitive> NewlistOfSensitiveTrue;
    ArrayList<Sensitive>listOfSensitiveUpdateTrue;
    ArrayList<Sensitive> listUpdateSensitive;
    Sensitive sensitiveUpdate;
    private boolean update=false;


    final String activity = " RepresentativeUpdateProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug", activity);
        setContentView(R.layout.activity_representative_update_product);
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase creference=FirebaseDatabase.getInstance();
        final String barcode = getIntent().getStringExtra("barcode");
        codebar = findViewById(R.id.updateBarcode_editTxt);

        nameCompany = findViewById(R.id.updateCompany_editTxt);
        infoProduct = findViewById(R.id.Updateinformation_editTxt);
        nameProduct = findViewById(R.id.updateNameProduct_editTxt);
        urlImage = findViewById(R.id.Updateimage_editTxt);
        weightSen = findViewById(R.id.updateWeight_editTxt);
        btnUpdate = findViewById(R.id.saveUpdate_button);
        mProgressBar= findViewById(R.id.progressBar_update_product);
        setCompanyName();

        if (barcode != null) { // if you get from update page
            isAdd=false;
            setDetails(barcode);
            codebar.setFocusable(false); // unable change the barcode

        }

        final String[] select_qualification = {"    בחר רגישויות  ", "ביצים", "בוטנים", "גלוטן", "אגוזים", "סויה", "לקטוז", "שומשום", "צנובר" , "חרדל" ,"סלרי" ,"שקדים"};
        spinnerUpdate=(Spinner) findViewById(R.id.updatesensitives_spinner);

        final ArrayList<Sensitive> listOfSensitive = new ArrayList<>();
        final ArrayList<Sensitive_Checkbox> listbox=new ArrayList<>();
        NewlistOfSensitiveTrue = new ArrayList<Sensitive>();
        listUpdateSensitive=new ArrayList<Sensitive>();
        listboxUpdate=new ArrayList<Sensitive_Checkbox>();

        for (int i = 0; i < select_qualification.length; i++) {
            sensitiveUpdate=new Sensitive();
            senUpdatebox=new Sensitive_Checkbox();
            sensitiveUpdate.setSensitiveType(select_qualification[i]);
            sensitiveUpdate.setsensitiveKey(Integer.toString(i-1));
            senUpdatebox.setSensitiveTypebox(select_qualification[i]);
//            updateSensitive(sensitiveUpdate,barcode);
            listUpdateSensitive.add(sensitiveUpdate);
            listboxUpdate.add(senUpdatebox);

            AddProduct_SensitivesCheckBoxSpinner updateProductSensitivesSpinner = new AddProduct_SensitivesCheckBoxSpinner(this, 0, listboxUpdate);
            spinnerUpdate.setAdapter(updateProductSensitivesSpinner);


            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        for (int j = 0; j < listboxUpdate.size(); j++) {
                            if (listboxUpdate.get(j).getSelectedbox() == true) {

                                NewlistOfSensitiveTrue.add(listUpdateSensitive.get(j));
                            }
                    }


                    product = new Product();
                    product.setBarcode(codebar.getText().toString());
                    product.setCompanyName(nameCompany.getText().toString());
                    product.setProductDescription(infoProduct.getText().toString());
                    product.setProductName(nameProduct.getText().toString());
                    product.setSensitiveList(NewlistOfSensitiveTrue);


                    product.setUrlImage(urlImage.getText().toString());
                    product.setWeightAndType(weightSen.getText().toString());

                        String barcode_new = codebar.getText().toString();
                        new FirebaseProductsHelper().updateProduct(barcode_new, product, new FirebaseProductsHelper.DataStatus() {
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
                                Toast.makeText(RepresentativeUpdateProductActivity.this, "המוצר התעדכן בהצלחה", Toast.LENGTH_LONG).show();
                                finish();
                                return;
                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }
            });
        }//for

    }

    private void updateSensitive(final Sensitive sensitive, String barcode)
    {
        DatabaseReference reference = firebaseDatabase.getReference("Products").child(barcode);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Product p = dataSnapshot.getValue(Product.class);
                for(int i=1; i<p.getSensitiveList().size(); i++)
                {
                    if(p.getSensitiveList().get(i).getSensitiveType().equals(sensitive.getSensitiveType()))
                    {
                        listboxUpdate.get(i).setSelectedbox(true);
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setDetails (String barcode) {

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
                listOfSensitiveUpdateTrue=new ArrayList<Sensitive>();
                for(int i=0; i<p.getSensitiveList().size(); i++)
                {
                    for(int j=1; j<listboxUpdate.size();j++)
                    {
                        if(listboxUpdate.get(j).getSensitiveTypebox().equals(p.getSensitiveList().get(i).getSensitiveType()))
                        {
                            listboxUpdate.get(j).setSelectedbox(true);
                            listUpdateSensitive.add(p.getSensitiveList().get(i));
                            sensitiveUpdate.setSensitiveType(listboxUpdate.get(j).getSensitiveTypebox());
                            sensitiveUpdate.setsensitiveKey(Integer.toString(i-1));
                            listOfSensitiveUpdateTrue.add(sensitiveUpdate);
                        }

                    }
                }
                p.setSensitiveList(listOfSensitiveUpdateTrue);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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