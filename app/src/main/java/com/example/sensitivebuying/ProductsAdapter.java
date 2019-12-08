package com.example.sensitivebuying;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductsAdapter extends ArrayAdapter<Products> {

    public ProductsAdapter(Context context, ArrayList<Products> products) {
        super(context, 0, products);
    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        // Get the data item for this position
//        Products product = getItem(position);
//
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_representative_search, parent, false);
//        }
//        // Lookup view for data population
//        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
//        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
//
//        // Populate the data into the template view using the data object
//        tvName.setText(product.getCompanyName());
//        tvHome.setText(product.getProductDescription());
//
//        // Return the completed view to render on screen
//        return convertView;
//    }
}
