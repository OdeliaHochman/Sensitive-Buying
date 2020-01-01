package com.example.sensitivebuying.ui.represntative;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensitivebuying.R;
import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.dataObject.RepresentativeUser;
import com.example.sensitivebuying.dataObject.User;
import com.example.sensitivebuying.firebaseHelper.FirebaseUserHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class RepresentativeStatisticsActivity extends AppCompatActivity {

    final String activity = "RepresentativeStatisticsActivity";

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference companyReference;
    private DatabaseReference sensitiveReference;

    private BarChart barchart;
    private int numOfChild;
    private ArrayList<BarEntry> barEnteries = new ArrayList<>();
    private ArrayList<String> theCompany = new ArrayList<>();

    private  ArrayList <Entry> proBySensPie = new ArrayList<>();
    private PieChart pieChart;
    private int numOfPro;
    private  ArrayList <String> sensitiveList = new ArrayList<>();

    private String companyName;
    private RepresentativeUser repUser;
    private ArrayList<String> companyBarcodes= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_statistics);

        firebaseDatabase = FirebaseDatabase.getInstance();

        barchart=(BarChart)findViewById(R.id.barchart_stat);
        pieChart = findViewById(R.id.piechart_senspro_stat);
        pieChart.setUsePercentValues(true);

        new FirebaseUserHelper().readUser(new FirebaseUserHelper.DataStatusUser() {
            @Override
            public void DataIsLoaded(User userHelper, String key) {
                repUser = (RepresentativeUser) userHelper;
                companyName =repUser.getCompanyName();
            }
        });

        companyReference=firebaseDatabase.getReference().child("Companies");

        companyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot company : dataSnapshot.getChildren())
                {
                    if((company.getKey()).equals(companyName))
                    {
                        for (DataSnapshot barcodes : company.getChildren())
                        {
                            String barcode = barcodes.getKey();
                            companyBarcodes.add(barcode);
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        companyReference.addValueEventListener(new ValueEventListener() {
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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        barchart.getAxisLeft().setAxisMinValue(0f);
        barchart.getAxisRight().setAxisMinValue(0f);
        barchart.getAxisLeft().setAxisMaxValue(10f);
        barchart.getAxisRight().setAxisMaxValue(10f);

        barchart.setTouchEnabled(true);
        barchart.setDragEnabled(true);
        //  barchart.setScaleEnabled(true);

        barchart.invalidate();



                    sensitiveReference = firebaseDatabase.getReference().child("ProductsBysensitive");

                    sensitiveReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int index=0;
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                String sens = child.getKey();
                                sensitiveList.add(sens);

                                for(DataSnapshot grandson : child.getChildren())
                                {
                                    if(companyBarcodes.contains(grandson.getKey()))
                                    {
                                        numOfPro++;
                                    }
                                }
                                proBySensPie.add(new Entry(numOfPro, index));
                                index++;
                            }

                            PieDataSet dataSet = new PieDataSet(proBySensPie, "מספר המוצרים המכילים את הרגישות");
                            PieData data = new PieData(sensitiveList, dataSet);
                            pieChart.setData(data);
                            pieChart.setDescription("");
                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            pieChart.animateXY(5000, 5000);

                            data.setValueFormatter(new PercentFormatter());
                            pieChart.setDrawHoleEnabled(true);
                            pieChart.setTransparentCircleRadius(40f);
                            pieChart.setHoleRadius(40f);
                            data.setValueTextSize(10f);
                            data.setValueTextColor(Color.DKGRAY);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




    }



}
