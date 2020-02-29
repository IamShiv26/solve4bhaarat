package com.example.eldercare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    public void sugar(View view){
        startActivity(new Intent(getApplicationContext(), Food.class));
        finish();
    }
    public void med(View view){
        startActivity(new Intent(getApplicationContext(), ReminderActivity.class));
        finish();
    }

    public void viewFood(View view){
        startActivity(new Intent(getApplicationContext(), ViewFood.class));
        finish();
    }
}
