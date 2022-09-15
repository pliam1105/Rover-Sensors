package com.pliamdev.pliam.roversensors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class SettingsActivity extends AppCompatActivity {
    public ActionBar actionbar;
    public ImageView primaryImage;
    public ImageView primaryDarkImage;
    public TextView colorText;
    public TextView behaviorText;
    public Switch quickSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        behaviorText = findViewById(R.id.textView6);
        colorText = findViewById(R.id.textView3);
        quickSwitch = findViewById(R.id.quick_launch_switch);
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setTitle("Settings");
        }
        primaryImage = findViewById(R.id.primaryColorImage);
        primaryDarkImage = findViewById(R.id.primaryDarkColorImage);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        quickSwitch.setChecked(sharedPref.getBoolean("quick_launch",true));
        quickSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("quick_launch",isChecked);
                editor.apply();
                Intent intent = new Intent();
                intent.setAction("com.pliamdev.pliam.roversensors.settings_change");
                sendBroadcast(intent);
            }
        });
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()){
            colorText.setTextColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            behaviorText.setTextColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            primaryImage.setImageDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            primaryDarkImage.setImageDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0"))));
            Window window = SettingsActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        primaryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(SettingsActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                builder.setTitle("Select Primary Color");
                builder.setPositiveButton("OK", new ColorEnvelopeListener() {
                    @Override
                    public void onColorSelected(ColorEnvelope colorEnvelope, boolean fromUser) {
                        String primaryString = "#"+colorEnvelope.getHexCode();
                        colorText.setTextColor(Color.parseColor(primaryString));
                        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primaryString)));
                        primaryImage.setImageDrawable(new ColorDrawable(Color.parseColor(primaryString)));
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("primaryColor",primaryString);
                        editor.apply();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        primaryDarkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(SettingsActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                builder.setTitle("Select Primary Color Dark");
                builder.setPositiveButton("OK", new ColorEnvelopeListener() {
                    @Override
                    public void onColorSelected(ColorEnvelope colorEnvelope, boolean fromUser) {
                        String primaryDarkString = "#"+colorEnvelope.getHexCode();
                        Window window = SettingsActivity.this.getWindow();
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.parseColor(primaryDarkString));
                        primaryDarkImage.setImageDrawable(new ColorDrawable(Color.parseColor(primaryDarkString)));
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("primaryColorDark",primaryDarkString);
                        editor.apply();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        quickSwitch.setChecked(sharedPref.getBoolean("quick_launch",true));
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()){
            colorText.setTextColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            behaviorText.setTextColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            primaryImage.setImageDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            primaryDarkImage.setImageDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0"))));
            Window window = SettingsActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
    }
}
