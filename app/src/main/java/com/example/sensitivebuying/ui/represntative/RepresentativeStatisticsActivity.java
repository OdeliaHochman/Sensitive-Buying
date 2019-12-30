package com.example.sensitivebuying.ui.represntative;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sensitivebuying.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class RepresentativeStatisticsActivity extends AppCompatActivity {

    final String activity = "RepresentativeStatisticsActivity";

    private BarChart barchart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_statistics);

        barchart=findViewById(R.id.barchart_stat);

        ArrayList<BarEntry> barEnteries = new ArrayList<>();
        barEnteries.add(new BarEntry(44f,0));
        barEnteries.add(new BarEntry(88f,1));

        BarDataSet barDataSet = new BarDataSet(barEnteries,"num of products");

        ArrayList<String> theCompany = new ArrayList<>();
        theCompany.add("strauss");
        theCompany.add("osem");


        BarData barData = new BarData(theCompany,barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barchart.setData(barData);

        barchart.setTouchEnabled(true);
        barchart.setDragEnabled(true);
        barchart.setScaleEnabled(true);

    }


}
