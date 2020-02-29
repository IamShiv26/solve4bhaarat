package com.example.eldercare;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSch extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        doBackgroundWork(params);
        return false;
    }

    public void doBackgroundWork(JobParameters params)
    {
        Calendar c = Calendar.getInstance();

        int Hr24=c.get(Calendar.HOUR_OF_DAY);

        DatabaseReference userDBRef;

        if(Hr24 == 3)
        {
            userDBRef = FirebaseDatabase.getInstance().getReference().child("Morning");
            userDBRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        String medicineName = ds.getValue().toString();
                        Log.d("hvjvh",medicineName);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
