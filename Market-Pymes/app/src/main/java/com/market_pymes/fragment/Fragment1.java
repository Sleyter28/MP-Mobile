package com.market_pymes.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.market_pymes.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Fragment1 extends Fragment {

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frag_home, container, false);
        BarChart barChart = (BarChart) rootView.findViewById(R.id.graf1);

        ArrayList<BarEntry> batEntries = new ArrayList<BarEntry>();
        batEntries.add(new BarEntry(44f,0));
        batEntries.add(new BarEntry(88f,1));
        batEntries.add(new BarEntry(66f,2));
        batEntries.add(new BarEntry(12f,3));
        batEntries.add(new BarEntry(19f,4));
        batEntries.add(new BarEntry(91f,5));

        BarDataSet barDataSet;

        barDataSet = new BarDataSet(batEntries,"Dates");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        ArrayList<IBarDataSet> theDates = new ArrayList<IBarDataSet>();
        theDates.add(barDataSet);

        BarData barData = new BarData(theDates);
        barData.setValueTextSize(12f);
        barData.setBarWidth(0.9f);
        barChart.setTouchEnabled(false);
        barChart.setData(barData);

        return rootView;
    }

}
