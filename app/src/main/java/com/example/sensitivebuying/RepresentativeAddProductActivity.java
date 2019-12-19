package com.example.sensitivebuying;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeAddProductActivity extends AppCompatActivity {

    private Button btnSave;
    private  FirebaseDatabaseHelper addFirebase;
    private EditText codebar;
    private EditText nameCompany;
    private EditText infoProduct;
    private EditText nameProduct;
    private Spinner sensitives;
    private EditText urlImage;
    private EditText weightSen;

    private DatabaseReference reference;

    final String activity = " RepresentativeAddProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_add_product);
        codebar= findViewById(R.id.barcode_editTxt);
        nameCompany=findViewById(R.id.company_editTxt);
        infoProduct=findViewById(R.id.information_editTxt);
        nameProduct=findViewById(R.id.nameProduct_editTxt);
        urlImage=findViewById(R.id.image_editTxt);
        weightSen=findViewById(R.id.weight_editTxt);
        btnSave =findViewById(R.id.save_button);

        final String[] select_qualification = {
                "Select Sensitive", "בוטנים", "אגוזים", "שקדים", "גלוטן","לקטוז","סויה", "שומשום"};
        Spinner spinner = (Spinner) findViewById(R.id.sensitives_spinner);

        ArrayList<StateVO> listVOs = new ArrayList<>();

        for (int i = 0; i < select_qualification.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        MyAdapter myAdapter = new MyAdapter(this, 0,listVOs);
        spinner.setAdapter(myAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final Product product= new Product();
                product.setBarcode(codebar.getText().toString());
                product.setCompanyName(nameCompany.getText().toString());
                product.setProductDescription(infoProduct.getText().toString());
                product.setProductName(nameProduct.getText().toString());
               // product.setSensitiveList(sensitives.getText().toString());
                product.setUrlImage(urlImage.getText().toString());
                product.setWeightAndType(weightSen.getText().toString());
               // reference= FirebaseDatabase.getInstance().getReference().child("Products").child(codebar.getText().toString());
                //reference.setValue(product);
                new FirebaseDatabaseHelper().addProduct(product, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Product> productsList, List<String> keys) {
                    }

                    @Override
                    public void DataIsInserted() {

                        Toast.makeText(RepresentativeAddProductActivity.this,"המוצר התווסף בהצלחה" , Toast.LENGTH_LONG).show();
                        finish();return;

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
