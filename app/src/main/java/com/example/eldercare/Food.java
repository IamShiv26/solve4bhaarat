package com.example.eldercare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Food extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinner1;
    private EditText select_date,select_time;
    TextView disp_date;
    String vital_value="";
    private int mYear, mMonth, mDay;
    private int day,month,year;
    private EditText value_count;
    private String date_time ="";
    private String time_day ="";
    private Button save;
    int value = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        save = (Button) findViewById(R.id.save);
        save.setText("SAVE AND CONTINUE "+"\u02C3");
        spinner1 = (Spinner) findViewById(R.id.vital_val);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vital_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

        value_count = (EditText) findViewById(R.id.vital_value);

        select_date = (EditText) findViewById(R.id.date_value);
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(select_date);
            }
        });

        select_time = (EditText)findViewById(R.id.time_value);
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(select_time);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Food.this,"SAVED",Toast.LENGTH_SHORT).show();
                if(TextUtils.isEmpty(value_count.getText())){
                    value_count.setError("Do not leave it empty");
                    return;
                }
                if(TextUtils.isEmpty(select_time.getText())){
                    value_count.setError("Do not leave it empty");
                    return;
                }
                if(TextUtils.isEmpty(select_date.getText())){
                    value_count.setError("Do not leave it empty");
                    return;
                }
                value = Integer.parseInt(value_count.getText().toString());

                SugarDets su = new SugarDets(
                        vital_value,
                        date_time,
                        time_day,
                        value
                );

                FirebaseDatabase.getInstance().getReference("Sugar Level")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(su).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Food.this, "Values added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        }
                    }
                });

            }
        });
    }

    private void showDateDialog(final EditText select_date){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                date_time = dayOfMonth+"/"+month+"/"+year;
                select_date.setText(date_time);
                //System.out.println(date_time);
            }
        };
        new DatePickerDialog(Food.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog(final EditText select_time){
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time_day = hourOfDay+":"+minute;
                select_time.setText(time_day);
                //System.out.println(time_day);
            }
        };
        new TimePickerDialog(Food.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        vital_value = text;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }


}
