package com.example.sensitivebuying;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_config {

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
        private TextView mSOME;
        private TextView mBarcode;
        private String key;

        public ProductItemView (ViewGroup parent)
        {
            super(LayoutInflater.from(mContext).inflate(R.layout.products_list_item,parent,false));

            mNamePro =(TextView)itemView.findViewById(R.id.productName);
            mWeight =(TextView)itemView.findViewById(R.id.weight);
            mSOME =(TextView)itemView.findViewById(R.id.companyName);
            mBarcode =(TextView)itemView.findViewById(R.id.barcode);
        }

        public void bind(Product product , String key)
        {
            mNamePro.setText(product.getProductName());
            mSOME.setText(product.getCompanyName());
            mWeight.setText(product.getWeightAndType());
            mBarcode.setText(product.getBarcode());
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
