package com.project.geekynehal.expensetrackerapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText email,pass;
    private TextView signin;
    private Button btnReg;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.sign_up_user);
        pass=findViewById(R.id.sign_up_pass);
        btnReg=findViewById(R.id.sign_up_btn);
        signin=findViewById(R.id.sign_in_btn);
        mAuth=FirebaseAuth.getInstance();
    }
}
