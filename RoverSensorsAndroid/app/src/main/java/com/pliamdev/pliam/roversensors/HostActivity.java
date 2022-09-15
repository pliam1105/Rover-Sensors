package com.pliamdev.pliam.roversensors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class HostActivity extends AppCompatActivity {
    public String uri;
    public EditText ip_text;
    public Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        Intent broadcast = new Intent();
        broadcast.setAction("com.pliamdev.pliam.roversensors.start");
        sendBroadcast(broadcast);
        Toolbar toolbar = findViewById(R.id.toolbar_host);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setTitle("Select Host");
        }
        ip_text = findViewById(R.id.ip_val);
        ip_text.setVisibility(View.GONE);
        spinner = findViewById(R.id.host_val);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.host_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ip_text.setVisibility(View.GONE);
                        uri = "pliamprojects.000webhostapp.com/rover";
                        break;
                    case 1:
                        ip_text.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        FloatingActionButton hostFab = findViewById(R.id.hostFab);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = HostActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()) {
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
            hostFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
        }
        hostFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemPosition() == 1) {
                    if (!ip_text.getText().toString().isEmpty()) {
                        uri = ip_text.getText().toString();
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("host", uri);
                        editor.apply();
                        startActivity(new Intent(HostActivity.this, ChartActivity.class));
                    } else {
                        Snackbar.make(findViewById(R.id.host_coordinator),"Please enter a valid ip address!",Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("host", uri);
                    editor.apply();
                    startActivity(new Intent(HostActivity.this, ChartActivity.class));
                }
            }
        });
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = HostActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        FloatingActionButton hostFab = findViewById(R.id.hostFab);
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()) {
            ActionBar actionbar = getSupportActionBar();
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
            hostFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
        }
    }
}
