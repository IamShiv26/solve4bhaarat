package com.example.eldercare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReminderActivity extends AppCompatActivity {
    EditText medicine,duration;
    CheckBox morning, afternoon, night;
    FirebaseAuth fAuth;
    Button submit, addMedicine;
    Boolean morn;
    Boolean aft;
    Boolean nigh;
    ArrayList<Medicine> mor;
    ArrayList<Medicine> af;
    ArrayList<Medicine> nig;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        medicine = findViewById(R.id.medicine);
        duration = findViewById(R.id.duration);
        morning = findViewById(R.id.morning);
        afternoon = findViewById(R.id.afternoon);
        night = findViewById(R.id.night);
        fAuth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.submit);
        morn = false;
        aft = false;
        nigh = false;
        mor = new ArrayList<Medicine>();
        af = new ArrayList<Medicine>();
        nig = new ArrayList<Medicine>();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Morning")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(mor).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference("AfterNoon")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(af).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseDatabase.getInstance().getReference("Night")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(nig).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                                                }
                                                else{
                                                    Toast.makeText(ReminderActivity.this, "Medicine addition UnSuccessful", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(ReminderActivity.this, "Medicine addition UnSuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(ReminderActivity.this, "Medicine addition UnSuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
}

    public void addMed(View view){
        final String medicine1 = medicine.getText().toString().trim();
        final String duration1 = duration.getText().toString().trim();
        if (morning.isChecked()) {
            morn = true;
        }
        if (afternoon.isChecked()) {
            aft = true;
        }
        if (night.isChecked()) {
            nigh = true;
        }

        if (TextUtils.isEmpty(medicine1)) {
            medicine.setError("Medicine is Required");
            return;
        }
        if (TextUtils.isEmpty(duration1)) {
            duration.setError("Duration is Required");
            return;
        }
        Medicine med=new Medicine(
                medicine1,
                duration1,
                morn,
                aft,
                nigh
        );
        if(morn)
        {
            mor.add(med);
        }
        if(aft)
        {
            af.add(med);
        }
        if(nigh)
        {
            nig.add(med);
        }

        medicine.setText("");
        duration.setText("");
        morning.setChecked(false);
        afternoon.setChecked(false);
        night.setChecked(false);
        morn=false;
        aft = false;
        nigh = false;
    }
}



