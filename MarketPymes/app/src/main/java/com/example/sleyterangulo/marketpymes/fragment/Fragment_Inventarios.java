package com.example.sleyterangulo.marketpymes.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sleyterangulo.marketpymes.R;

public class Fragment_Inventarios extends Fragment {

    public Fragment_Inventarios() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_inventarios, container, false);
        return viewRoot;
    }


}
