package com.example.sensitivebuying.ui.customer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sensitivebuying.R;
import com.example.sensitivebuying.RecyclerView_config;
import com.example.sensitivebuying.dataObject.CustomerUser;
import com.example.sensitivebuying.firebaseHelper.FirebaseUserHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ContactFragment extends Fragment implements  View.OnClickListener {


    final String activity = " ContactFragment";
    private Button send;
    private View v;
    private EditText fullName,phoneNumber,subject,content;



    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("debug",activity);
        v= inflater.inflate(R.layout.fragment_contact, container, false);

        fullName=(EditText)v.findViewById(R.id.fullname_contactUs_customer);
        phoneNumber=(EditText)v.findViewById(R.id.phonenumber_contactUs_customer);;
        subject=(EditText)v.findViewById(R.id.subject_contactUs_customer);;
        content=(EditText)v.findViewById(R.id.content_contactUs_customer);;


        send = (Button)v.findViewById(R.id.btn_send_contactUs_customer);
        send.setOnClickListener(this);

        return v;

    }

        private void sendEmail() {
        Log.i("Send email", "");

        String customerMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        String[] TO = {"odeliamos0@gmail.com"};
        String[] CC = {customerMail} ;
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
        emailIntent.putExtra(Intent.EXTRA_TEXT, fullNametS+"\n"+phoneNumberS+customerMail+"\n"+contentS);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
           // finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
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



