package com.pliamdev.pliam.roversensors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.AirMapPolyline;
import com.airbnb.android.airmapview.AirMapView;
import com.airbnb.android.airmapview.DefaultAirMapViewBuilder;
import com.airbnb.android.airmapview.MapType;
import com.airbnb.android.airmapview.listeners.OnMapInitializedListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MissionDetails extends AppCompatActivity {
    public ArrayList<LatLng> coordinates;
    public LatLng rover_pos;
    public String mission_id;
    public Toolbar toolbar;
    public AirMapView mapView;
    public DefaultAirMapViewBuilder mapViewBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_details);
        toolbar = findViewById(R.id.mission_details_toolbar);
        Intent i = getIntent();
        mission_id = i.getStringExtra("mission_id");
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setTitle("Mission Details");
            actionbar.setSubtitle("Rover Tracking");
        }
        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()){
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = MissionDetails.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        final SwipeRefreshLayout srl = findViewById(R.id.swiperefresh_mission_details);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ParseMissionDetails().execute(mission_id);
            }
        });
        srl.setRefreshing(true);
        mapViewBuilder = new DefaultAirMapViewBuilder(this);
        mapView = findViewById(R.id.mapView);
        mapView.initialize(getSupportFragmentManager());
        mapView.setOnMapInitializedListener(new OnMapInitializedListener() {
            @Override
            public void onMapInitialized() {
                mapView.setMapType(MapType.MAP_TYPE_SATELLITE);
                mapView.setMyLocationEnabled(false);
                new ParseMissionDetails().execute(mission_id);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class ParseMissionDetails extends AsyncTask<String, String, String> {

        final String TAG = "ParseMissionDetail.java";
        // contacts JSONArray
        JSONArray dataJsonArr = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                // set your json string url here
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                String yourJsonStringUrl = "https://"+sharedPref.getString("host","pliamprojects.000webhostapp.com/rover")+"/gps_data_json.php?mission_id="+arg0[0];

                coordinates=new ArrayList<>();
                // instantiate our json parser
                JsonParser jParser = new JsonParser();

                // get the array of data
                dataJsonArr = jParser.getJSONFromUrl(yourJsonStringUrl);
                if (dataJsonArr != null && dataJsonArr.length() > 0) {
                    // loop through all data
                    for (int i = 0; i < dataJsonArr.length(); i++) {
                        JSONObject c = dataJsonArr.getJSONObject(i);
                        String latitude = c.getString("latitude");
                        String longitude = c.getString("longitude");
                        coordinates.add(new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude)));
                        rover_pos=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            if(coordinates!=null&&coordinates.size()>0) {
                mapView = findViewById(R.id.mapView);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng coordinate : coordinates) {
                    builder.include(coordinate);
                }
                int padding = 50;
                LatLngBounds bounds = builder.build();
                mapView.setBounds(bounds, padding);
                mapView.addPolyline(new AirMapPolyline(null,coordinates, 5,10,Color.parseColor("#000000")));
                mapView.addMarker(new AirMapMarker.Builder<>().title("ROVER").position(rover_pos).build());
            }else{
                Snackbar.make(findViewById(R.id.mission_details_coordinator),"Not available location data!",Snackbar.LENGTH_LONG).show();
            }
            SwipeRefreshLayout srl = findViewById(R.id.swiperefresh_mission_details);
            srl.setRefreshing(false);
        }
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
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = MissionDetails.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
    }
}
