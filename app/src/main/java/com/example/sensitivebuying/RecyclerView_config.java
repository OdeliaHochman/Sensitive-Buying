package com.example.sensitivebuying;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.ui.customer.CustomerDetailsActivity;
import com.example.sensitivebuying.ui.represntative.RepresentativeProductDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class RecyclerView_config implements Serializable {

    private Context mContext;
    private ProductsAdapter mproductsAdapter;
    private ProductsAdapterCus mproductsAdapterCus;



    public void setConfig(RecyclerView recyclerView, Context context, List<Product> products , List<String> keys)
    {
        mContext=context;
        mproductsAdapter= new ProductsAdapter(products,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mproductsAdapter);
    }

    public void setConfigCus(RecyclerView recyclerView, Context context, List<Product> products , List<String> keys,List<String> barcodeRed )
    {
        mContext=context;
        mproductsAdapterCus= new ProductsAdapterCus(products,keys, barcodeRed);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mproductsAdapterCus);
    }


    class ProductItemView extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNamePro;
        private TextView mWeight;
        private TextView mCompanyName;
        private TextView mBarcode;
        private String key;
        private ImageView productIm;
        private boolean isRep;



        public ProductItemView (ViewGroup parent)
        {
            super(LayoutInflater.from(mContext).inflate(R.layout.products_list_item,parent,false));


            mNamePro =(TextView)itemView.findViewById(R.id.productName);
            mWeight =(TextView)itemView.findViewById(R.id.weight);
            mCompanyName =(TextView)itemView.findViewById(R.id.companyName);
            mBarcode =(TextView)itemView.findViewById(R.id.barcode);
            productIm = (ImageView)itemView.findViewById(R.id.imageView_productsList);

            checkIfIsRep();


        }

        public void bind(Product product , String barcode)
        {
            mNamePro.setText(product.getProductName());
            mCompanyName.setText(product.getCompanyName());
            mWeight.setText(product.getWeightAndType());
            mBarcode.setText(barcode);
            //mBarcode.setText(product.getBarcode());
            Picasso.get().load(product.getUrlImage()).into(productIm);


        }

        private void checkIfIsRep()
        {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference uidRef = rootRef.child("Users").child(uid);
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("rep").getValue(Boolean.class).equals(true))
                    {isRep=true; }
                    else
                    { isRep=false;}
                    itemView.setOnClickListener(ProductItemView.this);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            uidRef.addListenerForSingleValueEvent(valueEventListener); //?

        }

        @Override
        public void onClick(View v) {

            if ( v== itemView){
                Intent intent;
                if(isRep)
                {
                    intent = new Intent(mContext, RepresentativeProductDetailsActivity.class);
                }
                else
                {
                    intent = new Intent(mContext, CustomerDetailsActivity.class);
                }

                // intent.putExtra("key",key);
                intent.putExtra("product name",mNamePro.getText().toString());
                intent.putExtra("weight",mWeight.getText().toString());
                intent.putExtra("company name",mCompanyName.getText().toString());
                intent.putExtra("barcode",mBarcode.getText().toString());

                mContext.startActivity(intent);

            }
        }
    }

    class ProductsAdapter extends  RecyclerView.Adapter<ProductItemView>
    {
        private List<Product> mproductsList;
        private List<String> barcodes;

        public ProductsAdapter(List<Product> mproductsList, List<String> barcodes)
        {
            this.mproductsList = mproductsList;
            this.barcodes = barcodes;
        }

        @NonNull
        @Override
        public ProductItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new ProductItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductItemView holder, int position)
        {
            holder.bind(mproductsList.get(position), barcodes.get(position));

        }

        @Override
        public int getItemCount()
        {
            return mproductsList.size();
        }
    }



    class ProductsAdapterCus extends  RecyclerView.Adapter<ProductItemView>
    {
        private List<Product> mproductsList;
        private List<String> barcodes;

        private List<String> barcodesRed;

        public ProductsAdapterCus(List<Product> mproductsList, List<String> barcodes, List<String> barcodesRed) {
            this.mproductsList = mproductsList;
            this.barcodes = barcodes;
            this.barcodesRed = barcodesRed;
        }

        @NonNull
        @Override
        public ProductItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new ProductItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductItemView holder, int position)
        {
            holder.bind(mproductsList.get(position), barcodes.get(position));
            if (barcodesRed.contains(barcodes.get(position))){
                holder.mNamePro.setTextColor(Color.rgb(253,5,26));
            }
            else
                holder.mNamePro.setTextColor(Color.rgb(80,196,85));


        }

        @Override
        public int getItemCount()
        {
            return mproductsList.size();
        }
    }

}
