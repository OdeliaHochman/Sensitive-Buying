package com.example.sensitivebuying;

import android.annotation.SuppressLint;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RepresentativeHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView companyProI , statI ,contactI ,logOutI;
   private ImageView bgapp, clover;
    private LinearLayout textsplash, texthome, menus;
    private Animation frombottom;
   // private TextView companyProT , statT ,contactT ,logOutT;


    final String activity = " RepresentativeHomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_home);


        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        menus = (LinearLayout) findViewById(R.id.menus);

        bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(400);
        clover.animate().alpha(0).setDuration(800).setStartDelay(700);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(400);


        menus.startAnimation(frombottom);

        companyProI = (ImageView) findViewById(R.id.companyProductsImage);
        companyProI.setOnClickListener(this);

        statI = (ImageView) findViewById(R.id.statImage);
        statI.setOnClickListener(this);

        contactI = (ImageView) findViewById(R.id.contactImage);
        contactI.setOnClickListener(this);

        logOutI = (ImageView) findViewById(R.id.logOutImage);
        logOutI.setOnClickListener(this);

//        companyProT = (TextView) findViewById(R.id.companyProductsName);
//        companyProT.setOnClickListener(this);
//
//        statT = (TextView) findViewById(R.id.statName);
//        statT.setOnClickListener(this);
//
//        contactT = (TextView) findViewById(R.id.contactName);
//        contactT.setOnClickListener(this);
//
//        logOutT = (TextView)findViewById(R.id.logOutName);
//        logOutT.setOnClickListener(this);


    }

     @Override
        public void onClick(View v)
        {

            if(logOutI == v)
            {
                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
                finish();
           }

            if(companyProI == v)
            {
                Intent intent =new Intent(RepresentativeHomeActivity.this,RepresentativeSearchActivity.class);
                startActivity(intent);
            }
            if(statI == v)
            {
                Intent intent =new Intent(RepresentativeHomeActivity.this,RepresentativeStatisticsActivity.class);
                startActivity(intent);
            }
            if(contactI == v)
            {
                Intent intent =new Intent(RepresentativeHomeActivity.this,RepresentativeContactUsActivity.class);
                startActivity(intent);
            }

//            if(companyProT == v)
//            {
//                Intent intent =new Intent(RepresentativeHomeActivity.this,RepresentativeSearchActivity.class);
//                startActivity(intent);
//            }
//            if(statT == v)
//            {
//                Intent intent =new Intent(RepresentativeHomeActivity.this,RepresentativeStatisticsActivity.class);
//                startActivity(intent);
//            }
//            if(contactT == v)
//            {
//                Intent intent =new Intent(RepresentativeHomeActivity.this,RepresentativeContactUsActivity.class);
//                startActivity(intent);
//            }

        }
}