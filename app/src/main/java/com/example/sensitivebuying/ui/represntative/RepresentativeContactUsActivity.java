package com.example.sensitivebuying.ui.represntative;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sensitivebuying.R;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class RepresentativeContactUsActivity extends AppCompatActivity implements   View.OnClickListener {

final String activity = "RepresentativeContactUsActivity";
private Button send;
private EditText fullName,phoneNumber,subject,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debug",activity);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_contact_us);

        fullName=(EditText)findViewById(R.id.fullname_contactUs_rep);
        phoneNumber=(EditText)findViewById(R.id.phonenumber_contactUs_rep);
        subject=(EditText)findViewById(R.id.subject_contactUs_rep);
        content=(EditText)findViewById(R.id.content_contactUs_rep);

        send = (Button)findViewById(R.id.btn_send_contactUs_rep);
        send.setOnClickListener(this);
    }

   private void sendEmail() {
        Log.i("Send email", "");

        String repMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        String[] TO = {"odeliamos0@gmail.com"};
        String[] CC = {repMail} ;
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        String subjectS = subject.getText().toString();
        String phoneNumberS = phoneNumber.getText().toString();
        String fullNametS = fullName.getText().toString();
        String contentS = content.getText().toString();

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectS);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "full name: "+fullNametS+"\n"+"phone number: "+phoneNumberS+"\n"+"mail: "+repMail+"\n"+"content: "+contentS);

        try {
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        finish();
        Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
        Toast.makeText(RepresentativeContactUsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
        }

@Override
public void onClick(View v) {

        if(v==send)
        {
        sendEmail();
        }

        }

}







