package com.example.sensitivebuying.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sensitivebuying.R;

public class RepresentativeContactActivity extends AppCompatActivity {


    final String activity = "RepresentativeContactActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_contact);
    }


//    private void sendEmail() {
//        Log.i("Send email", "");
//
//        String[] TO = {"someone@gmail.com"};
//        String[] CC = {"xyz@gmail.com"};
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.setType("text/plain");
//
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
//
//        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            finish();
//            Log.i("Finished sending email", "");
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(RepresentativeContactActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
//    }
}
