package com.example.sensitivebuying.firebaseHelper;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.dataObject.RepresentativeUser;
import com.example.sensitivebuying.dataObject.Sensitive;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.OnDisconnect;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseProductsBySensitiveHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private int num=0; // num of adding product to sensitive
    ArrayList<String> barcodes = new ArrayList<>();
    // private List<Product> companiesList = new ArrayList<>();

    public interface DataStatus
    {
        void DataIsLoaded(ArrayList<String> barcodes);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseProductsBySensitiveHelper()
    {
        mDatabase =FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference("ProductsBysensitive");
    }

    public void readProductsOfSen(Sensitive sensitives , final DataStatus dataStatus)
    {

        String senstivieName = sensitives.getSensitiveType();
        mReference.child(senstivieName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot keyNode:dataSnapshot.getChildren())
                {
                    barcodes.add(keyNode.getValue(String.class));

                }
                dataStatus.DataIsLoaded(barcodes);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


    }


    public void addProductOfCompanies(Product product,final DataStatus dataStatus)
    {
        String  barcodeCompany = product.getBarcode();
        final ArrayList<Sensitive> sensitives = product.getSensitiveList();
        for (Sensitive s : sensitives){
            String senName = s.getSensitiveType();
            mReference.child(senName).child(barcodeCompany).setValue(barcodeCompany).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    num++;
                    if (num == sensitives.size()) {
                        dataStatus.DataIsInserted();
                        num = 0;
                    }
                }
            });
        }

    }

    public void updateSensitiveProduct(Product product,final DataStatus dataStatus)
    {
        String barcode= product.getBarcode();
        String nameSensitive="";
        final ArrayList<Sensitive> sensitives= product.getSensitiveList();
        for(int i=0;  i<product.getSensitiveList().size();i++)
        {
            nameSensitive=sensitives.get(i).getSensitiveType();
            mReference.child(nameSensitive).child(barcode).setValue(barcode).addOnSuccessListener(new OnSuccessListener<Void>()
            {
                @Override
                public void onSuccess(Void aVoid)
                {
                    num++;
                    if (num == sensitives.size())
                    {
                        dataStatus.DataIsInserted();
                        num = 0;
                    }
                }
            });
        }

    }

    public void deleteProduct(Product p,final DataStatus dataStatus) {
        String barcode = p.getBarcode();
        final ArrayList<Sensitive> sensitives = p.getSensitiveList();
        for (Sensitive s : sensitives) {
            String senName = s.getSensitiveType();
            mReference.child(senName).child(barcode).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    num++;
                    if (num == sensitives.size()) {
                        dataStatus.DataIsDeleted();
                        num = 0;
                    }
                }
            });
        }
    }
}
