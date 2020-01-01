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
import com.example.sensitivebuying.dataObject.Sensitive;
import com.example.sensitivebuying.dataObject.SenstivieListFinal;
import com.example.sensitivebuying.dataObject.User;
import com.example.sensitivebuying.firebaseHelper.FirebaseCompaniesHelper;
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsHelper;
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
import java.util.List;


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
    private  ArrayList <String> sensitiveList = new ArrayList<>();

    private String companyName;
    private RepresentativeUser repUser;
    private List<String> companyBarcodes= new ArrayList<>();
    private ArrayList<Sensitive> allSensitives= SenstivieListFinal.getSensitiveListFinal();
    private int size = allSensitives.size() +1;
    private int [] arrayFrequencySen = new int[size];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        repUser = (RepresentativeUser) getIntent().getSerializableExtra("RepresentativeUser"); // recive obj from last activity

        setContentView(R.layout.activity_representative_statistics);

        firebaseDatabase = FirebaseDatabase.getInstance();

        barchart=(BarChart)findViewById(R.id.barchart_stat);
        pieChart = findViewById(R.id.piechart_senspro_stat);
        pieChart.setUsePercentValues(true);

        companyName= repUser.getCompanyName();



        companyReference=firebaseDatabase.getReference().child("Companies");

// bar chart
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

        fillStringSensitives();

        new FirebaseCompaniesHelper().readProductsOfCompanie(companyName, new FirebaseCompaniesHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<String> barcodesList) {

                companyBarcodes=barcodesList;

                new FirebaseProductsHelper().readProductByBarcode(companyBarcodes, new FirebaseProductsHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Product> productsList, List<String> keys) {

                        for (Product p : productsList) {

                            fillArrayOfSenByProduct(p);
                        }

                        for ( int i=0 ; i<arrayFrequencySen.length; i++) {
                            proBySensPie.add(new Entry(arrayFrequencySen[i], i));

                        }

                        PieDataSet dataSet = new PieDataSet(proBySensPie, "מספר המוצרים המכילים את הרגישות");
                        PieData data = new PieData(sensitiveList, dataSet);
                        pieChart.setData(data);
                        pieChart.setDescription("");
                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        pieChart.animateXY(5000, 5000);

                        data.setValueFormatter(new PercentFormatter());
                        pieChart.setDrawHoleEnabled(true);
                        pieChart.setTransparentCircleRadius(60f);
                        pieChart.setHoleRadius(40f);
                        data.setValueTextSize(10f);
                        data.setValueTextColor(Color.DKGRAY);


                    }

                    @Override
                    public void ProductDataLoaded(Product product) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });





            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });




                        }





    private void fillArrayOfSenByProduct (Product p){


        ArrayList<Sensitive> senOfProduct= p.getSensitiveList();
        if ( senOfProduct==null)
            arrayFrequencySen[size-1]++; // last elemnt
        else {
            for (Sensitive sen : senOfProduct) {
                int ind = Integer.valueOf(sen.getsensitiveKey());
                arrayFrequencySen[ind]++;
            }
        }

    }


    private void fillStringSensitives ( ){

        for ( Sensitive sen : allSensitives) {
            String senName = sen.getSensitiveType();
            sensitiveList.add(senName);
        }

        sensitiveList.add("ללא רגישיות");

    }



}
