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
import android.widget.EditText;

public class PassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        Toolbar toolbar = findViewById(R.id.toolbar_pass);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setTitle("Enter Password");
        }
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()){
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
            FloatingActionButton goFab = findViewById(R.id.goFab);
            goFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = PassActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        FloatingActionButton goFab = findViewById(R.id.goFab);
        goFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText passText = findViewById(R.id.password);
                String pass = passText.getText().toString();
                if(pass.equals("R0ver2018gr")){
                    startActivity(new Intent(PassActivity.this,NewMissionActivity.class));
                }else{
                    Snackbar.make(findViewById(R.id.pass_coordinator),"Wrong Password!",Snackbar.LENGTH_LONG).show();
                }
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
        ActionBar actionbar = getSupportActionBar();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()){
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
            FloatingActionButton goFab = findViewById(R.id.goFab);
            goFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = PassActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
    }
}
