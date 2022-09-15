package com.pliamdev.pliam.roversensors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import androidx.browser.customtabs.CustomTabsIntent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class NewMissionActivity extends AppCompatActivity {

    public String planet_id = null;
    public String start_datetime = null;
    public String end_datetime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mission);
        Toolbar toolbar = findViewById(R.id.toolbar_new_mission);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setTitle("Add Mission");
        }
        Button planetBut = findViewById(R.id.planetIdButton);
        Button startBut = findViewById(R.id.startDateTime);
        Button endBut = findViewById(R.id.endDateTime);
        FloatingActionButton missionFab = findViewById(R.id.missionFab);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if (!sharedPref.getString("primaryColor", "#0000ff").isEmpty()) {
            if (actionbar != null) {
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor", "#0000ff"))));
            }
            missionFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sharedPref.getString("primaryColor", "#0000ff"))));
            planetBut.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor", "#0000ff")));
            startBut.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor", "#0000ff")));
            endBut.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor", "#0000ff")));
        }
        if (!sharedPref.getString("primaryColorDark", "#0000f0").isEmpty()) {
            Window window = NewMissionActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark", "#0000f0")));
        }
        startBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(NewMissionActivity.this, DateTimeSelect.class);
                startActivityForResult(p, 6);
            }
        });
        endBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(NewMissionActivity.this, DateTimeSelect.class);
                startActivityForResult(p, 7);
            }
        });
        missionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (planet_id != null) {
                    if (start_datetime != null) {
                        if (end_datetime != null) {
                            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                            String url = "https://" + sharedPref.getString("host", "pliamprojects.000webhostapp.com/rover") + "/mission_manager.php?mission_start=" + start_datetime + "&mission_end=" + end_datetime + "&planet_id=" + planet_id;
                            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor", "#0000ff")));
                            CustomTabsIntent customTabsIntent = builder.build();
                            customTabsIntent.launchUrl(NewMissionActivity.this, Uri.parse(url));
                        } else {
                            Snackbar.make(findViewById(R.id.new_mission_coordinator),"No end date time selected!",Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(findViewById(R.id.new_mission_coordinator),"No start date time selected!",Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(R.id.new_mission_coordinator),"No planet selected!",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        planetBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(NewMissionActivity.this, PlanetSelectActivity.class);
                startActivityForResult(p, 2);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {

            if (resultCode == RESULT_OK) {
                Integer m_id = data.getIntExtra("planet_id", 0);
                planet_id = Integer.toString(m_id);
                TextView planetIdText = findViewById(R.id.planetIdText);
                planetIdText.setText(data.getStringExtra("planet_name"));
            }
            if (resultCode == RESULT_CANCELED) {
                Snackbar.make(findViewById(R.id.new_mission_coordinator),"No planet selected!",Snackbar.LENGTH_LONG).show();
                //Write your code if there's no result
            }
        }
        if (requestCode == 6) {

            if (resultCode == RESULT_OK) {
                start_datetime = data.getStringExtra("datetime");
                TextView startText = findViewById(R.id.startText);
                startText.setText(start_datetime);
            }
            if (resultCode == RESULT_CANCELED) {
                Snackbar.make(findViewById(R.id.new_mission_coordinator),"No start date time selected!",Snackbar.LENGTH_LONG).show();
                //Write your code if there's no result
            }
        }
        if (requestCode == 7) {

            if (resultCode == RESULT_OK) {
                end_datetime = data.getStringExtra("datetime");
                TextView endText = findViewById(R.id.endText);
                endText.setText(end_datetime);
            }
            if (resultCode == RESULT_CANCELED) {
                Snackbar.make(findViewById(R.id.new_mission_coordinator),"No end date time selected!",Snackbar.LENGTH_LONG).show();
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    protected void onRestart() {
        super.onRestart();
        ActionBar actionbar = getSupportActionBar();
        Button planetBut = findViewById(R.id.planetIdButton);
        FloatingActionButton missionFab = findViewById(R.id.missionFab);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if (!sharedPref.getString("primaryColor", "#0000ff").isEmpty()) {
            if (actionbar != null) {
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor", "#0000ff"))));
            }
            missionFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sharedPref.getString("primaryColor", "#0000ff"))));
            planetBut.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor", "#0000ff")));
        }
        if (!sharedPref.getString("primaryColorDark", "#0000f0").isEmpty()) {
            Window window = NewMissionActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark", "#0000f0")));
        }
    }
}
