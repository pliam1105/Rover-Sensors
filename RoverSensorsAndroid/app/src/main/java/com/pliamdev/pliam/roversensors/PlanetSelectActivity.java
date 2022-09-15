package com.pliamdev.pliam.roversensors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlanetSelectActivity extends AppCompatActivity {

    public ArrayList<String> planet;
    public ArrayList<String> missions;
    public ListView mission_list;
    public ArrayAdapter<String> mission_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_select);
        Toolbar toolbar = findViewById(R.id.toolbar_planet_select);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setTitle("Select Planet");
        }
        mission_list=findViewById(R.id.planet_list);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = PlanetSelectActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()) {
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
        }
        final SwipeRefreshLayout srl = findViewById(R.id.swiperefresh_planet_select);
        srl.setRefreshing(true);
        new ParsePlanetList().execute();
        mission_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem == 0){
                    srl.setEnabled(true);
                }else{
                    srl.setEnabled(false);
                }
            }
        });
        mission_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("planet_id",position+1);
                returnIntent.putExtra("planet_name",(String)mission_list.getItemAtPosition(position));
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ParsePlanetList().execute();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED,returnIntent);
        finish();
    }

    public class ParsePlanetList extends AsyncTask<String, String, String> {

        final String TAG = "ParsePlanetList.java";
        // set your json string url here
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        String yourJsonStringUrl = "https://"+sharedPref.getString("host","pliamprojects.000webhostapp.com/rover")+"/planets_json.php";

        // contacts JSONArray
        JSONArray dataJsonArr = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                missions=new ArrayList<>();
                planet=new ArrayList<>();
                // instantiate our json parser
                JsonParser jParser = new JsonParser();

                // get the array of data
                dataJsonArr = jParser.getJSONFromUrl(yourJsonStringUrl);
                if (dataJsonArr != null && dataJsonArr.length() > 0) {
                    // loop through all data
                    for (int i = 0; i < dataJsonArr.length(); i++) {
                        JSONObject c = dataJsonArr.getJSONObject(i);
                        planet.add(c.getString("Name"));
                        missions.add("Planet: "+planet.get(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            if (missions != null && missions.size() > 0) {
                mission_adapter=new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item,missions);
                mission_list.setAdapter(mission_adapter);
                mission_adapter.notifyDataSetChanged();
            }
            SwipeRefreshLayout srl = findViewById(R.id.swiperefresh_planet_select);
            srl.setRefreshing(false);
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = PlanetSelectActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()) {
            ActionBar actionbar = getSupportActionBar();
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
        }
    }
}
