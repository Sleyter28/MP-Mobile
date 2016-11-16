package com.example.sleyterangulo.marketpymes.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sleyterangulo.marketpymes.R;

public class Fragment1 extends Fragment {

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frag_home, container, false);
//        BarChart barChart = (BarChart) rootView.findViewById(R.id.graf1);
//
//        ArrayList<BarEntry>batEntries = new ArrayList<>();
//        batEntries.add(new BarEntry(44f,0));
//        batEntries.add(new BarEntry(88f,1));
//        batEntries.add(new BarEntry(66f,2));
//        batEntries.add(new BarEntry(12f,3));
//        batEntries.add(new BarEntry(19f,4));
//        batEntries.add(new BarEntry(91f,5));
//
//        BarDataSet barDataSet = new BarDataSet(batEntries,"Dates");
//
//        ArrayList<String> theDates = new ArrayList<>();
//        theDates.add("April");
//        theDates.add("May");
//        theDates.add("June");
//        theDates.add("July");
//        theDates.add("August");
//        theDates.add("September");
//
//        BarData barData = new BarData(theDates, barDataSet);
//        barChart.setData(barData);

        return rootView;
    }

}
