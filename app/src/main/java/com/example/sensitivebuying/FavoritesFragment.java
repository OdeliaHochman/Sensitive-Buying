package com.example.sensitivebuying;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    final String activity = " FavoritesFragment";
    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("debug",activity);
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

}
