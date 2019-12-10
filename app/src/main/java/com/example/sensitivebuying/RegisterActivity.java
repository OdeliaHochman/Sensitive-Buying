package com.example.sensitivebuying;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText EditTextEmail;
    private EditText EditTextPassword;

    private Button ButtonRegister;

    private TextView TextViewLogin;
    private EditText editViewName;
    private ProgressBar mProgressBar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usersReference= FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        EditTextEmail = (EditText) findViewById(R.id.emailRegister_editTxt);
        EditTextPassword = (EditText) findViewById(R.id.passwordRegister_editTxt);
        ButtonRegister = (Button) findViewById(R.id.register_button);
        mProgressBar =(ProgressBar)  findViewById(R.id.pb_loading);
        TextViewLogin = (TextView) findViewById(R.id.login_txtView);
        editViewName = findViewById(R.id.nameRegister_editTxt);

        // if the user already login
        if(firebaseAuth.getCurrentUser()==null ) {
        }

        ButtonRegister.setOnClickListener(this);
        TextViewLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==ButtonRegister)
            register();
        if(v==TextViewLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void register (){

        if(isEmpty()) return;
        inProgress(true);
        final String email = EditTextEmail.getText().toString();
        String password = EditTextPassword.getText().toString();
        final String name = editViewName.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegisterActivity.this,"ההרשמה הצליחה", Toast.LENGTH_LONG).show();
                CustomerUser user = new CustomerUser(name, email,null,null);
                usersReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                Intent intent = new Intent(RegisterActivity.this,SensitiveRegisterActivity.class);
                intent.putExtra("CustomerUser", user);
                startActivity(intent);
                inProgress(false);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                inProgress(false);
                Toast.makeText(RegisterActivity.this,"ההרשמה נכשלה"+e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    // deal with button and progressBar inProgress
    private void  inProgress ( boolean flag) {

        if(flag){

            mProgressBar.setVisibility(View.VISIBLE);
            ButtonRegister.setEnabled(false);
        }

        else {

            mProgressBar.setVisibility(View.GONE);
            ButtonRegister.setEnabled(true);
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
        if (TextUtils.isEmpty(editViewName.getText().toString())) {
            Toast.makeText(this, "לא הוכנס שם", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
