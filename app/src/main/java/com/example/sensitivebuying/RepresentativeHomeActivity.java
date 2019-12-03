package com.example.sensitivebuying;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RepresentativeHomeActivity extends AppCompatActivity {

    private Button btnLogin =(Button)findViewById(R.id.login_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_home);

    }

    //@Override
    public void onClick(View v)
    {

        if(btnLogin == v)
        {
            Intent intent =new Intent(RepresentativeHomeActivity.this, LoginActivity.class);
            String fname = intent.getExtras().getString("fname");
            String lname = intent.getExtras().getString("lname");
            startActivity(intent);
        }
    }
}
