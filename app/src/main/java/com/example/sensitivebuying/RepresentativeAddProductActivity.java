package com.example.sensitivebuying;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RepresentativeAddProductActivity extends AppCompatActivity{

    private Button ButtonUpdateProduct;
    private  FirebaseDatabaseHelper addFirebase;
    private EditText codebar;
    private EditText nameCompany;
    private EditText infoProduct;
    private EditText nameProduct;
    private EditText sensitives;
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
        sensitives=findViewById(R.id.senstive_editTxt);
        urlImage=findViewById(R.id.image_editTxt);
        weightSen=findViewById(R.id.weight_editTxt);
        ButtonUpdateProduct=findViewById(R.id.update_button);
        reference= FirebaseDatabase.getInstance().getReference().child("Products");
        final Product product= new Product();
        ButtonUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                product.setBarcode(codebar.getText().toString().trim());
                product.setCompanyName(nameCompany.getText().toString().trim());
                product.setProductDescription(infoProduct.getText().toString().trim());
                product.setProductName(infoProduct.getText().toString().trim());
                product.setSensitiveList(sensitives.getText().toString().trim());
                product.setUrlImage(urlImage.getText().toString().trim());
                product.setWeightAndType(weightSen.getText().toString().trim());
                reference.push().setValue(product);
            }
        });
    }

}
