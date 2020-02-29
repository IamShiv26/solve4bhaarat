package com.example.eldercare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    EditText mName, mEmail, mPassword, mAge;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mName = findViewById(R.id.name);
        mAge = findViewById(R.id.age);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        mRegisterBtn = findViewById(R.id.submit);

        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String name = mName.getText().toString().trim();
                final String age = mAge.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(name))
                {
                    mName.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(age))
                {
                    mAge.setError("Age is Required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is Required");
                    return;
                }
                if(password.length()< 6)
                {
                    mPassword.setError("Password length must be >=6");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegisterActivity.this, "Resgistration Successful", Toast.LENGTH_SHORT).show();
                            User user = new User(
                                    name,
                                    email,
                                    age
                            );
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Resgistration Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                                    }
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Resgistration UnSuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
