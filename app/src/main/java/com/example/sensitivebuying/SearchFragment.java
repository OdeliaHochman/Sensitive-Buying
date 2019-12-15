package com.example.sensitivebuying;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements  View.OnClickListener {

    final String activity = " SearchFragment";

    private  View v;
    // private TextView companyProT , statT ,contactT ,logOutT;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("debug",activity);
        v= inflater.inflate(R.layout.fragment_search, container, false);


        return  v;

    }


    @Override
    public void onClick(View v) {

    }
}
