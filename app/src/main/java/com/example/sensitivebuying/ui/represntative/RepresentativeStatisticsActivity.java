package com.example.sensitivebuying.ui.represntative;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensitivebuying.R;
import com.example.sensitivebuying.dataObject.CustomerUser;
import com.example.sensitivebuying.dataObject.Product;
import com.example.sensitivebuying.dataObject.RepresentativeUser;
import com.example.sensitivebuying.dataObject.Sensitive;
import com.example.sensitivebuying.dataObject.SenstivieListFinal;
import com.example.sensitivebuying.dataObject.User;
import com.example.sensitivebuying.firebaseHelper.FirebaseCompaniesHelper;
import com.example.sensitivebuying.firebaseHelper.FirebaseProductsHelper;
import com.example.sensitivebuying.firebaseHelper.FirebaseSenstiveUserHelper;
import com.example.sensitivebuying.firebaseHelper.FirebaseUserHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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
    private  ArrayList <String> sensitiveList1 = new ArrayList<>();


    private String companyName;
    private RepresentativeUser repUser;
    private List<String> companyBarcodes= new ArrayList<>();
    List<String> uids = new ArrayList<>();

    private ArrayList<Sensitive> allSensitives= SenstivieListFinal.getSensitiveListFinal();
    private int size = allSensitives.size() +1;
    private int [] arrayFrequencySenProducts = new int[size];
    private int [] arrayFrequencySenUsers = new int[size];
    private ArrayList<Sensitive> userSenstive;

    private PieChart pieChartUserSen;
    private  ArrayList <Entry> userBySensPieEntry = new ArrayList<>();
    private int num;




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
        pieChartUserSen = findViewById(R.id.user_senstivies_piechart);
        pieChartUserSen.setUsePercentValues(true);


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
                        ArrayList<String> toDelete = new ArrayList<>();

                        for (int i = 0; i< arrayFrequencySenProducts.length; i++) {
                            if ( arrayFrequencySenProducts[i] != 0)
                            proBySensPie.add(new Entry(arrayFrequencySenProducts[i], i));
                            else {
                                String delete = sensitiveList.get(i);
                                toDelete.add(delete);
                            }

                        }
                        sensitiveList.removeAll(toDelete);


                        PieDataSet dataSet = new PieDataSet(proBySensPie, "");
                        dataSet.setSliceSpace(5f);
                        dataSet.setColors(MY_COLOR);
                        PieData data = new PieData(sensitiveList, dataSet);
                        pieChart.setData(data);

                        Legend l = pieChart.getLegend();
                        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT );
                        l.setWordWrapEnabled(true);

                        l.setFormSize(6f); // set the size of the legend forms/shapes
                        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
                        l.setTextSize(12f);
                        l.setTextColor(Color.BLACK);
//                        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
//                        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis




                        pieChart.setDescription("");
                        dataSet.setColors(MY_COLOR);
                        pieChart.animateXY(5000, 5000);

                        data.setValueFormatter(new PercentFormatter());
                        pieChart.setDrawHoleEnabled(false);
                        pieChart.setTransparentCircleRadius(100f);
                        pieChart.setHoleRadius(5f);
                        data.setValueTextSize(15f);
                        data.setValueTextColor(Color.DKGRAY);


                        pieChart.invalidate();


//                       pieChart.setDrawEntryLabels(false);

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


        DatabaseReference customersUsers = firebaseDatabase.getReference("UserByRole").child("customers");

        customersUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                num =0;
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    String uid = keyNode.getValue(String.class);
                    uids.add(uid);
                }

                for ( String uid : uids) {
                    new FirebaseSenstiveUserHelper().readSensitiveSpecUser(uid, new FirebaseSenstiveUserHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(ArrayList<Sensitive> sensitives, ArrayList<String> keys) {
                            fillArrayOfSenByUser(sensitives);
                            num++;
                            if ( num == uids.size()) {
                                ArrayList<String> toDelete = new ArrayList<>();

                                for (int i = 0; i < arrayFrequencySenUsers.length; i++) {
                                    if (arrayFrequencySenUsers[i]!=0)
                                    userBySensPieEntry.add(new Entry(arrayFrequencySenUsers[i], i));
                                    else {
                                        String delete = sensitiveList1.get(i);
                                        toDelete.add(delete);
                                    }

                                }

                                sensitiveList1.removeAll(toDelete);

                                PieDataSet dataSetUser = new PieDataSet(userBySensPieEntry, "");
                                dataSetUser.setSliceSpace(5f);
                                dataSetUser.setColors(MY_COLOR);


                                PieData dataUser = new PieData(sensitiveList1, dataSetUser);
                                pieChartUserSen.setData(dataUser);

                                Legend l = pieChart.getLegend();
                                l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT );
                                l.setWordWrapEnabled(true);

                                l.setFormSize(6f); // set the size of the legend forms/shapes
                                l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
                                l.setTextSize(12f);
                                l.setTextColor(Color.BLACK);

                                pieChartUserSen.setDescription("");
                                pieChartUserSen.animateXY(5000, 5000);

                                dataUser.setValueFormatter(new PercentFormatter());
                                pieChartUserSen.setDrawHoleEnabled(false);
                                pieChartUserSen.setTransparentCircleRadius(100f);
                                pieChartUserSen.setHoleRadius(5f);
                                dataUser.setValueTextSize(15f);
                                dataUser.setValueTextColor(Color.DKGRAY);
                                num=0;

                                pieChartUserSen.invalidate();


                            }

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



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }





    private void fillArrayOfSenByProduct (Product p){


        ArrayList<Sensitive> senOfProduct= p.getSensitiveList();
        if ( senOfProduct==null)
            arrayFrequencySenProducts[size-1]++; // last element "אין רגישיות"
        else {
            for (Sensitive sen : senOfProduct) {
                int ind = Integer.valueOf(sen.getsensitiveKey());
                arrayFrequencySenProducts[ind]++;
            }
        }

    }


    private void fillArrayOfSenByUser ( ArrayList<Sensitive> senOfProduct){


        if ( senOfProduct==null)
            arrayFrequencySenUsers[size-1]++; // last element "אין רגישיות"
        else {
            for (Sensitive sen : senOfProduct) {
                int ind = Integer.valueOf(sen.getsensitiveKey());
                arrayFrequencySenUsers[ind]++;
            }
        }

    }


    private void fillStringSensitives ( ){

        for ( Sensitive sen : allSensitives) {
            String senName = sen.getSensitiveType();
            sensitiveList.add(senName);
        }

        sensitiveList.add("ללא רגישיות");

         sensitiveList1 = new ArrayList<>(sensitiveList);


    }


    public static final int[] MY_COLOR = {
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53),
            Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80)
    };

}
