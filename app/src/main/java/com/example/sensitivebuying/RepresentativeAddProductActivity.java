package com.example.sensitivebuying;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    final String activity = " RepresentativeAddProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug", activity);
        setContentView(R.layout.activity_representative_add_product);
        codebar = findViewById(R.id.barcode_editTxt);
        nameCompany = findViewById(R.id.company_editTxt);
        infoProduct = findViewById(R.id.information_editTxt);
        nameProduct = findViewById(R.id.nameProduct_editTxt);
        urlImage = findViewById(R.id.image_editTxt);
        weightSen = findViewById(R.id.weight_editTxt);
        btnSave = findViewById(R.id.save_button);

        final String[] select_qualification = {"    בחר רגישויות  ", "בוטנים", "אגוזים", "שקדים", "גלוטן", "לקטוז", "סויה", "שומשום"};
        spinner = (Spinner) findViewById(R.id.sensitives_spinner);

        final ArrayList<Sensitive> listOfSensitive = new ArrayList<>();
        final ArrayList<Sensitive> listOfSensitiveTrue = new ArrayList<>();


        for (int i = 0; i < select_qualification.length; i++) {
            final Sensitive sensitive = new Sensitive();
            sensitive.setSensitiveType(select_qualification[i]);
            listOfSensitive.add(sensitive);


            AddProduct_SensitivesCheckBoxSpinner addProductSensitivesSpinner = new AddProduct_SensitivesCheckBoxSpinner(this, 0, listOfSensitive);
            spinner.setAdapter(addProductSensitivesSpinner);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Sensitive> ArrSensitives=new ArrayList<>();
                    for (int j = 0; j < listOfSensitive.size(); j++) {
                        if (listOfSensitive.get(j).getSelected() == true)
                        {
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
                }
            });


        }

    }
}
