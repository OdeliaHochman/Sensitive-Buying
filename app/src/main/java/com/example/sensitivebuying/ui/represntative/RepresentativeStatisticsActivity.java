package com.example.sensitivebuying.ui.represntative;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensitivebuying.R;
import com.example.sensitivebuying.firebaseHelper.FirebaseCompaniesHelper;
import com.github.mikephil.charting.charts.BarChart;
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
    private HashMap<String,Integer> compPro;
    private FirebaseCompaniesHelper firebaseCompaniesHelper;
    private DatabaseReference cReference;
    private int  numOfChild;
    private String compName;
    private int numOfCompany;
    private ArrayList<BarEntry> barEnteries = new ArrayList<>();
    private ArrayList<String> theCompany = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_statistics);

        barchart=(BarChart)findViewById(R.id.barchart_stat);
        numOfCompany=0;
        numOfChild=0;

        firebaseDatabase = FirebaseDatabase.getInstance();
        cReference=firebaseDatabase.getReference().child("Companies");

        cReference.child(compName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    numOfChild = (int)dataSnapshot.getChildrenCount();
                }
                else
                {
                    numOfChild=0;
                }
                numOfCompany++; //???????????????????????????????????????
                theCompany.add(compName);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        for(int i=0 ; i< numOfCompany ;i++)
        {
            barEnteries.add(new BarEntry(numOfChild,i));
        }

       // barEnteries.add(new BarEntry(44f,0));
      //  barEnteries.add(new BarEntry(88f,1));

        // theCompany.add("strauss");
        //   theCompany.add("osem");

        BarDataSet barDataSet = new BarDataSet(barEnteries,"מספר מוצרים לפי חברה");


        BarData barData = new BarData(theCompany,barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barchart.setData(barData);

        barchart.setTouchEnabled(true);
        barchart.setDragEnabled(true);
        barchart.setScaleEnabled(true);

    }


}
