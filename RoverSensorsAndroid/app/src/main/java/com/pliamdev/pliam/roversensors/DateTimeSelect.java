package com.pliamdev.pliam.roversensors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TimePicker;

public class DateTimeSelect extends AppCompatActivity {

    public String date;
    public String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_select);
        Button select_button = findViewById(R.id.button);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = DateTimeSelect.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()) {
            select_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
        }
        CalendarView calendarView = findViewById(R.id.calendarView);
        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay+":"+minute+":00";
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year+"/"+(month+1)+"/"+dayOfMonth;
            }
        });
        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date!=null) {
                    if(time!=null) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("datetime", date + " " + time);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }else{
                        Snackbar.make(findViewById(R.id.datetime_select_coordinator),"Please select time!",Snackbar.LENGTH_LONG).show();
                    }
                }else{
                    Snackbar.make(findViewById(R.id.datetime_select_coordinator),"Please select date!",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED,returnIntent);
        finish();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = DateTimeSelect.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()) {
            Button select_button = findViewById(R.id.button);
            select_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
        }
    }
}
