package com.example.sensitivebuying;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText EditTextEmail;
    private EditText EditTextPassword;
    private Button ButtonLogin;
    private TextView TextViewRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        EditTextEmail =  (EditText) findViewById(R.id.email_editTxt);
        EditTextPassword =  (EditText) findViewById(R.id.password_editTxt);
        ButtonLogin =  (Button) findViewById(R.id.login_button);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        TextViewRegister = (TextView) findViewById(R.id.register_txtView);

        // if the user already login go to search activity
        if(firebaseAuth.getCurrentUser()!=null ) {
            Intent intent = new Intent(this, CustomerSearchActivity.class);
            startActivity(intent);
            finish(); return;
        }

        ButtonLogin.setOnClickListener(this);
        TextViewRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v ==  ButtonLogin)
            login();

        if (v== TextViewRegister){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }

    }

    // login
    private void login (){
        if(isEmpty())
            return;
        inProgress(true);
        String email = EditTextEmail.getText().toString();
        String password = EditTextPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this,"התחברת בהצלחה", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, CustomerSearchActivity.class);
                startActivity(intent);
                finish(); return;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                inProgress(false);
                Toast.makeText(LoginActivity.this,"ההתחברות נכשלה"+e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    // deal with button and progressBar inProgress
    private void  inProgress ( boolean flag) {

        if(flag){

            mProgressBar.setVisibility(View.VISIBLE);
            ButtonLogin.setEnabled(false);
        }

        else {

            mProgressBar.setVisibility(View.GONE);
            ButtonLogin.setEnabled(true);
        }
    }

    // return if one of the text view is empty
    private  boolean isEmpty () {
        if (TextUtils.isEmpty(EditTextEmail.getText().toString())) {
            Toast.makeText(this, "לא הוכנס מייל", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(EditTextPassword.getText().toString())) {
            Toast.makeText(this, "לא הוכנסה סיסמה", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
