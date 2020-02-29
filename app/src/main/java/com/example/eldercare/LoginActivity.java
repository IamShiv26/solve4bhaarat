package com.example.eldercare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText mName, mEmail, mPassword, mAge;
    Button mLoginBtn;
    TextView registerLink;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mName = findViewById(R.id.name);
        mAge = findViewById(R.id.age);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.submit);
        registerLink = findViewById(R.id.registerLink);

        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();
        }

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password length must be >=6");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}
