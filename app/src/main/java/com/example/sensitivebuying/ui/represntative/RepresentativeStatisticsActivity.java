package com.example.sensitivebuying.ui.represntative;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensitivebuying.R;
import com.example.sensitivebuying.firebaseHelper.FirebaseCompaniesHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RepresentativeStatisticsActivity extends AppCompatActivity {

    final String activity = "RepresentativeStatisticsActivity";
    private FirebaseDatabase firebaseDatabase;
    private BarChart barchart;
    private DatabaseReference cReference;
    private int numOfChild;
    private ArrayList<BarEntry> barEnteries = new ArrayList<>();
    private ArrayList<String> theCompany = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_statistics);

        barchart=(BarChart)findViewById(R.id.barchart_stat);


        firebaseDatabase = FirebaseDatabase.getInstance();
        cReference=firebaseDatabase.getReference().child("Companies");

        cReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index=0;
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    String companyName = keyNode.getKey();
                    theCompany.add(companyName);
                    numOfChild = (int)keyNode.getChildrenCount();
                    barEnteries.add(new BarEntry(numOfChild,index));
                    index++;
                }

                BarDataSet barDataSet = new BarDataSet(barEnteries,"מספר מוצרים לפי חברה");
                BarData barData = new BarData(theCompany,barDataSet);
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                barchart.animateY(1500);
                barchart.setDescription("");
                barchart.setData(barData);


//                YAxis yAxisLeft = barchart.getAxisLeft();
//                yAxisLeft.setAxisMinValue(0f);
//                yAxisLeft.setAxisMaxValue(16f);

//                YAxis yAxisRight = barchart.getAxisRight();
//                yAxisRight.setAxisMinValue(0f);
//                yAxisRight.setAxisMaxValue(16f);

                barchart.setTouchEnabled(true);
                barchart.setDragEnabled(true);
                barchart.setScaleEnabled(true);

                barchart.invalidate();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


}
