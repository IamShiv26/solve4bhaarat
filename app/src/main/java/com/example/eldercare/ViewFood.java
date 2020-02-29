package com.example.eldercare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewFood extends AppCompatActivity {

    private DatabaseReference mDatabase,mDatabase2;
    SugarDets sd;
    FoodDets fd;
    int diff;
    StringBuilder builder;
    ArrayList<String> arr =  new ArrayList<String>();
    TextView tv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);
        tv = findViewById(R.id.foodss);
//        listView = (ListView) findViewById(R.id.dispFood);

//        textView.setTextSize(50);
        builder = new StringBuilder();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Sugar Level");
        mDatabase2= FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    sd = ds.getValue(SugarDets.class);
                    SugarDets sd1 = new SugarDets();
                    sd1.setSugar_level(sd.getSugar_level());
                    if(sd.getSugar_level() < 100)
                    {
                        diff = 100-sd.getSugar_level();
                    }
                    if(sd.getSugar_level() > 100)
                    {
                        diff = sd.getSugar_level()-100;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    fd = ds.getValue(FoodDets.class);
                    FoodDets fd1 = new FoodDets();
                    fd1.setSugars(fd.getSugars());
                    fd1.setFood_and_Serving(fd.getFood_and_Serving());
                    fd1.setFood_Type(fd.getFood_Type());

                    if(fd.getSugars()>diff)
                    {
                        builder.append(fd.getFood_and_Serving()+"\n");
//                        String str = builder.toString();
//                        System.out.println(str);
                    }
                }
                tv.setText(builder.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println(builder.toString());
//        setContentView(textView);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,  arr);
//        listView.setAdapter(adapter);

    }
}
