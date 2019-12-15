package com.example.sensitivebuying;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class RecyclerView_config implements Serializable {

    private Context mContext;
    private ProductsAdapter mproductsAdapter;

    public void setConfig(RecyclerView recyclerView,Context context, List<Product> products ,List<String> keys)
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


        }

        public void bind(Product product , String key)
        {
            mNamePro.setText(product.getProductName());
            mCompanyName.setText(product.getCompanyName());
            mWeight.setText(product.getWeightAndType());
            mBarcode.setText(product.getBarcode());
            Picasso.get().load(product.getUrlImage()).into(productIm);
            this.key=key;
        }

    }

    class ProductsAdapter extends  RecyclerView.Adapter<ProductItemView>
    {
        private List<Product> mproductsList;
        private List<String> mKeys;

        public ProductsAdapter(List<Product> mproductsList, List<String> mKeys)
        {
            this.mproductsList = mproductsList;
            this.mKeys = mKeys;
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
            holder.bind(mproductsList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mproductsList.size();
        }
    }

}
