package com.project.geekynehal.expensetrackerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
        signin=findViewById(R.id.sign_in_text);
        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail=email.getText().toString().trim();
                String mPass=pass.getText().toString().trim();
                if (TextUtils.isEmpty(mEmail))
                {
                    email.setError("Required Field!");
                    return ;
                }
                if (TextUtils.isEmpty(mPass))
                {
                    pass.setError("Required Field!");
                    return ;
                }
                mDialog.setMessage("Processing..");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful())
                             {
                                 startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                 Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                 mDialog.dismiss();
                             }
                             else
                             {
                                 Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                 mDialog.dismiss();
                             }
                    }
                });
            }
        });
    }
}
