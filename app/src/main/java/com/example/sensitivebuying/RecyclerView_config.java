package com.example.sensitivebuying;

import android.content.Context;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class RecyclerView_config implements Serializable {

    private Context mContext;
    private ProductsAdapter mproductsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Product> products , List<String> keys)
    {
        mContext=context;
        mproductsAdapter= new ProductsAdapter(products,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mproductsAdapter);
    }


    class ProductItemView extends RecyclerView.ViewHolder {

        private TextView mNamePro;
        private TextView mWeight;
        private TextView mCompanyName;
        private TextView mBarcode;
        private String key;
        private ImageView productIm;


        public ProductItemView (ViewGroup parent)
        {
            super(LayoutInflater.from(mContext).inflate(R.layout.products_list_item,parent,false));

            mNamePro =(TextView)itemView.findViewById(R.id.productName);
            mWeight =(TextView)itemView.findViewById(R.id.weight);
            mCompanyName =(TextView)itemView.findViewById(R.id.companyName);
            mBarcode =(TextView)itemView.findViewById(R.id.barcode);
            productIm = (ImageView)itemView.findViewById(R.id.imageView_productsList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RepresentativeProductDetailsActivity.class);
                   // Intent intent = new Intent(mContext, CustomerDetailsActivity.class);
                   // intent.putExtra("key",key);
                    intent.putExtra("product name",mNamePro.getText().toString());
                    intent.putExtra("weight",mWeight.getText().toString());
                    intent.putExtra("company name",mCompanyName.getText().toString());
                    intent.putExtra("barcode",mBarcode.getText().toString());


                    mContext.startActivity(intent);

                }
            });

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

}
