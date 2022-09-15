package com.pliamdev.pliam.roversensors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;

public class ChartActivity extends AppCompatActivity {
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    public String mission_id;
    public ArrayList<String> dataArray;
    public ArrayList<String> temp_array;
    public ArrayList<String> humid_array;
    public ArrayList<String> light_array;
    public ArrayList<String> gas_array;
    public ArrayList<String> co2_array;
    public ArrayList<String> tvoc_array;
    public ArrayList<String> uv_array;
    public ArrayList<String> bmp_array;
    public ArrayList<String> alt_array;
    public ArrayList<String> rad_array;
    public ArrayList<ILineDataSet> lineDataSets;
    public LineChart lineChart;
    private DrawerLayout mDrawerLayout;
    public Toolbar toolbar;
    public Boolean drawerOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        drawerOpened = sharedPref.getBoolean("drawer_opened",false);
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()){
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = ChartActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        final SwipeRefreshLayout srl = findViewById(R.id.swiperefresh);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTaskParseJson().execute(mission_id);
            }
        });
        lineChart = findViewById(R.id.chart);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                NavigationView nav_view = findViewById(R.id.nav_view);
                Menu nav_menu = nav_view.getMenu();
                View web_item_view = nav_menu.findItem(R.id.nav_web).getActionView();
                View contact_item_view = nav_menu.findItem(R.id.nav_contact).getActionView();
                View questionnaire_item_view = nav_menu.findItem(R.id.nav_questionnaire).getActionView();
                View settings_item_view = nav_menu.findItem(R.id.nav_settings).getActionView();
                View mission_item_view = nav_menu.findItem(R.id.nav_mission_details).getActionView();
                int[] w_loc = new int[2];
                web_item_view.getLocationOnScreen(w_loc);
                int[] c_loc = new int[2];
                contact_item_view.getLocationOnScreen(c_loc);
                int[] q_loc = new int[2];
                questionnaire_item_view.getLocationOnScreen(q_loc);
                int[] s_loc = new int[2];
                settings_item_view.getLocationOnScreen(s_loc);
                int[] m_loc = new int[2];
                mission_item_view.getLocationOnScreen(m_loc);
                final FancyShowCaseView web_showcase = new FancyShowCaseView.Builder(ChartActivity.this).title("Click \"Website Home\" to visit our website's home page!").focusRectAtPosition(w_loc[0]-nav_view.getWidth()/2,w_loc[1]+10,nav_view.getWidth(),60).focusShape(FocusShape.ROUNDED_RECTANGLE).roundRectRadius(90).titleGravity(Gravity.TOP|Gravity.CENTER).showOnce("Website Home").build();
                final FancyShowCaseView contact_showcase = new FancyShowCaseView.Builder(ChartActivity.this).title("Click \"Contact us\" to send us an email with questions and notes for us to improve your experience!").focusRectAtPosition(c_loc[0]-nav_view.getWidth()/2,c_loc[1]+10,nav_view.getWidth(),60).focusShape(FocusShape.ROUNDED_RECTANGLE).roundRectRadius(90).titleGravity(Gravity.TOP|Gravity.CENTER).showOnce("Contact Us").build();
                final FancyShowCaseView questionnaire_showcase = new FancyShowCaseView.Builder(ChartActivity.this).title("Click \"Questionnaire\" to answer our questionnaire about life in other planets!").focusRectAtPosition(q_loc[0]-nav_view.getWidth()/2,q_loc[1]+10,nav_view.getWidth(),60).focusShape(FocusShape.ROUNDED_RECTANGLE).roundRectRadius(90).titleGravity(Gravity.TOP|Gravity.CENTER).showOnce("Questionnaire").build();
                final FancyShowCaseView settings_showcase = new FancyShowCaseView.Builder(ChartActivity.this).title("Click \"Settings\" to customize app's look and behaviour!").focusRectAtPosition(s_loc[0]-nav_view.getWidth()/2,s_loc[1]+10,nav_view.getWidth(),60).focusShape(FocusShape.ROUNDED_RECTANGLE).roundRectRadius(90).titleGravity(Gravity.TOP|Gravity.CENTER).showOnce("Settings").build();
                final FancyShowCaseView mission_details_showcase = new FancyShowCaseView.Builder(ChartActivity.this).title("Click \"Mission Details\" to get information about the mission and see the rover's route!").focusRectAtPosition(m_loc[0]-nav_view.getWidth()/2,m_loc[1]+10,nav_view.getWidth(),60).focusShape(FocusShape.ROUNDED_RECTANGLE).roundRectRadius(90).titleGravity(Gravity.TOP|Gravity.CENTER).showOnce("Mission Details").build();
                FancyShowCaseQueue queue = new FancyShowCaseQueue().add(web_showcase).add(contact_showcase).add(questionnaire_showcase).add(settings_showcase).add(mission_details_showcase);
                queue.show();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("drawer_opened",true);
                editor.apply();
                drawerOpened = true;
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("drawer_opened",false);
                editor.apply();
                drawerOpened = false;
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        menuItem.setChecked(false);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected

                        // Handle item selection
                        switch (menuItem.getItemId()) {
                            case R.id.nav_web:
                                Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pliamprojects.000webhostapp.com/rover"));
                                startActivity(w);
                                return true;
                            case R.id.nav_contact:
                                Intent c = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pliamprojects.000webhostapp.com/rover/contact/"));
                                startActivity(c);
                                return true;
                            case R.id.nav_questionnaire:
                                Intent q = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pliamprojects.000webhostapp.com/rover/questionnaire"));
                                startActivity(q);
                                return true;
                            case R.id.nav_settings:
                                Intent s = new Intent(ChartActivity.this, SettingsActivity.class);
                                startActivity(s);
                                return true;
                            case R.id.nav_mission_details:
                                if(mission_id!=null){
                                    Intent m = new Intent(ChartActivity.this,MissionDetails.class);
                                    m.putExtra("mission_id",mission_id);
                                    startActivity(m);
                                }else{
                                    Snackbar.make(findViewById(R.id.content_frame),"No mission selected!",Snackbar.LENGTH_LONG).show();
                                }
                                return true;
                        }

                        return true;
                    }
                });
    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("drawer_opened",false);
        editor.apply();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        if(!isMicrophoneAvailable()) {
            menu.findItem(R.id.voice_rec).setEnabled(false).setIcon(R.drawable.ic_no_mic);
        }
        boolean ret = super.onCreateOptionsMenu(menu);
        final FancyShowCaseView select_mission_showcase = new FancyShowCaseView.Builder(ChartActivity.this).title("Click \"Select Mission\" to select a mission of which data will be loaded to chart!").focusCircleAtPosition(toolbar.getWidth()-(toolbar.getHeight()*2)-15,toolbar.getHeight()-35,toolbar.getHeight()/2).showOnce("Select Mission").build();
        final FancyShowCaseView planet_details_showcase = new FancyShowCaseView.Builder(ChartActivity.this).title("Click \"Planet Details\" to see more details about the planet where the rover collects data!").focusCircleAtPosition(toolbar.getWidth()-toolbar.getHeight()-30,toolbar.getHeight()-35,toolbar.getHeight()/2).showOnce("Planet Details").build();
        FancyShowCaseQueue queue = new FancyShowCaseQueue().add(select_mission_showcase).add(planet_details_showcase);
        queue.show();
        return ret;
    }

    public boolean isMicrophoneAvailable(){
        PackageManager pm = getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0);
        return activities.size() != 0;
    }

    public void startVoiceRecognitionActivity(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"What do you want me to do?");
        startActivityForResult(intent,VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        for (int i = 0; i < menu.size(); i++){
            menu.getItem(i).setVisible(!drawerOpened);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.new_mission:
                Intent i = new Intent(ChartActivity.this,PassActivity.class);
                startActivity(i);
                return true;
            case R.id.planet_details:
                if(mission_id!=null){
                    Intent p = new Intent(ChartActivity.this,PlanetActivity.class);
                    p.putExtra("mission_id",mission_id);
                    startActivity(p);
                }else{
                    Snackbar.make(findViewById(R.id.content_frame),"No mission selected!",Snackbar.LENGTH_LONG).show();
                }
                return true;
            case R.id.select_mission:
                Intent mission = new Intent(ChartActivity.this, MissionSelect.class);
                startActivityForResult(mission, 1);
                return true;
            case R.id.voice_rec:
                startVoiceRecognitionActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                Integer m_id = data.getIntExtra("mission_id", 0);
                mission_id = Integer.toString(m_id);
                SwipeRefreshLayout srl = findViewById(R.id.swiperefresh);
                srl.setRefreshing(true);
                new AsyncTaskParseJson().execute(mission_id);
            }
            if (resultCode == RESULT_CANCELED) {
                Snackbar.make(findViewById(R.id.content_frame),"No mission selected!",Snackbar.LENGTH_LONG).show();
                //Write your code if there's no result
            }
        }else if (requestCode == VOICE_RECOGNITION_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if(matches.contains("add mission") || matches.contains("create mission")){
                    Intent i = new Intent(ChartActivity.this,PassActivity.class);
                    startActivity(i);
                }else if (matches.contains("select mission")){
                    Intent mission = new Intent(ChartActivity.this, MissionSelect.class);
                    startActivityForResult(mission, 1);
                }else if (matches.contains("planet details") || matches.contains("Planet details")){
                    if(mission_id!=null){
                        Intent p = new Intent(ChartActivity.this,PlanetActivity.class);
                        p.putExtra("mission_id",mission_id);
                        startActivity(p);
                    }else{
                        Snackbar.make(findViewById(R.id.content_frame),"No mission selected!",Snackbar.LENGTH_LONG).show();
                    }
                }else if(matches.contains("refresh")){
                    SwipeRefreshLayout srl = findViewById(R.id.swiperefresh);
                    srl.setRefreshing(true);
                    new AsyncTaskParseJson().execute(mission_id);
                }else if(matches.contains("website") || matches.contains("website home") || matches.contains("Website") || matches.contains("Website home")){
                    Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pliamprojects.000webhostapp.com/rover"));
                    startActivity(w);
                }else if(matches.contains("contact") || matches.contains("contact us") || matches.contains("Contact") || matches.contains("Contact us")){
                    Intent c = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pliamprojects.000webhostapp.com/rover/contact/"));
                    startActivity(c);
                }else if(matches.contains("questionnaire") || matches.contains("Questionnaire")){
                    Intent q = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pliamprojects.000webhostapp.com/rover/questionnaire"));
                    startActivity(q);
                }else if(matches.contains("settings") || matches.contains("Settings")){
                    Intent s = new Intent(ChartActivity.this, SettingsActivity.class);
                    startActivity(s);
                }else{
                    Snackbar.make(findViewById(R.id.content_frame),"Couldn't recognize voice command!",Snackbar.LENGTH_LONG).show();
                }
            }else if (resultCode == RESULT_CANCELED){
                Snackbar.make(findViewById(R.id.content_frame),"Couldn't recognize voice command!",Snackbar.LENGTH_LONG).show();
            }
        }
    }//onActivityResult

    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        final String TAG = "AsyncTaskParseJson.java";
        // data JSONArray
        JSONArray dataJsonArr = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                lineDataSets = new ArrayList<>();
                dataArray = new ArrayList<>();
                temp_array = new ArrayList<>();
                humid_array = new ArrayList<>();
                light_array = new ArrayList<>();
                gas_array = new ArrayList<>();
                co2_array = new ArrayList<>();
                tvoc_array = new ArrayList<>();
                uv_array = new ArrayList<>();
                bmp_array = new ArrayList<>();
                alt_array = new ArrayList<>();
                rad_array = new ArrayList<>();
                // instantiate our json parser
                JsonParser jParser = new JsonParser();

                // get the array of data
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                dataJsonArr = jParser.getJSONFromUrl("https://"+sharedPref.getString("host","pliamprojects.000webhostapp.com/rover")+"/data_json.php?mission_id=" + arg0[0]);
                if (dataJsonArr != null && dataJsonArr.length() > 0) {
                    // loop through all data
                    for (int i = 0; i < dataJsonArr.length(); i++) {

                        JSONObject c = dataJsonArr.getJSONObject(i);

                        // Storing each json item in variable
                        String temperature = c.getString("AVG(temperature)");
                        String humidity = c.getString("AVG(humidity)");
                        String light = c.getString("AVG(light)");
                        String gas = c.getString("AVG(gas)");
                        String co2 = c.getString("AVG(CO2)");
                        String tvoc = c.getString("AVG(TVOC)");
                        String uv = c.getString("AVG(UV)");
                        String bmp = c.getString("AVG(barometric_pressure)");
                        String altitude = c.getString("AVG (altitude)");
                        String radiation = c.getString("AVG (radiation)");
                        temp_array.add(temperature);
                        humid_array.add(humidity);
                        light_array.add(light);
                        gas_array.add(gas);
                        co2_array.add(co2);
                        tvoc_array.add(tvoc);
                        uv_array.add(uv);
                        bmp_array.add(bmp);
                        alt_array.add(altitude);
                        rad_array.add(radiation);
                        dataArray.add("Temperature: " + temperature + ", Humidity: " + humidity + ", Light: " + light + ", Gas: " + gas + ", CO2: " + co2 + ", TVOC: " + tvoc + ", UV: " + uv + ", Barometric Pressure: " + bmp + ", Altitude: " + altitude + ", Radiation: " + radiation);
                    }

                    ArrayList<Entry> temp = new ArrayList<>();
                    ArrayList<Entry> humid = new ArrayList<>();
                    ArrayList<Entry> light = new ArrayList<>();
                    ArrayList<Entry> gas = new ArrayList<>();
                    ArrayList<Entry> co2 = new ArrayList<>();
                    ArrayList<Entry> tvoc = new ArrayList<>();
                    ArrayList<Entry> uv = new ArrayList<>();
                    ArrayList<Entry> bmp = new ArrayList<>();
                    ArrayList<Entry> alt = new ArrayList<>();
                    ArrayList<Entry> rad = new ArrayList<>();
                    int numDataPoints = dataArray.size();

                    for (int i = 0; i < numDataPoints; i++) {
                        float temp_val = Float.parseFloat(temp_array.get(i));
                        float humid_val = Float.parseFloat(humid_array.get(i));
                        float light_val = Float.parseFloat(light_array.get(i));
                        float gas_val = Float.parseFloat(gas_array.get(i));
                        float co2_val = Float.parseFloat(co2_array.get(i));
                        float tvoc_val = Float.parseFloat(tvoc_array.get(i));
                        float uv_val = Float.parseFloat(uv_array.get(i));
                        float bmp_val = Float.parseFloat(bmp_array.get(i));
                        float alt_val = Float.parseFloat(alt_array.get(i));
                        float rad_val = Float.parseFloat(rad_array.get(i));
                        temp.add(new Entry(i, temp_val));
                        humid.add(new Entry(i, humid_val));
                        light.add(new Entry(i, light_val));
                        gas.add(new Entry(i, gas_val));
                        co2.add(new Entry(i, co2_val/10));
                        tvoc.add(new Entry(i, tvoc_val));
                        uv.add(new Entry(i, uv_val));
                        bmp.add(new Entry(i, bmp_val / 1000));
                        alt.add(new Entry(i, alt_val));
                        rad.add(new Entry(i, rad_val));
                    }

                    LineDataSet lineDataSet1 = new LineDataSet(humid, "Hum");
                    lineDataSet1.setDrawCircles(false);
                    lineDataSet1.setColor(Color.BLUE);

                    LineDataSet lineDataSet2 = new LineDataSet(temp, "Temp");
                    lineDataSet2.setDrawCircles(false);
                    lineDataSet2.setColor(Color.RED);

                    LineDataSet lineDataSet3 = new LineDataSet(light, "Light");
                    lineDataSet3.setDrawCircles(false);
                    lineDataSet3.setColor(Color.YELLOW);

                    LineDataSet lineDataSet4 = new LineDataSet(gas, "Gas");
                    lineDataSet4.setDrawCircles(false);
                    lineDataSet4.setColor(Color.parseColor("#000000"));

                    LineDataSet lineDataSet5 = new LineDataSet(co2, "CO2");
                    lineDataSet5.setDrawCircles(false);
                    lineDataSet5.setColor(Color.LTGRAY);

                    LineDataSet lineDataSet6 = new LineDataSet(tvoc, "TVOC");
                    lineDataSet6.setDrawCircles(false);
                    lineDataSet6.setColor(Color.parseColor("#00ff00"));

                    LineDataSet lineDataSet7 = new LineDataSet(uv, "UV");
                    lineDataSet7.setDrawCircles(false);
                    lineDataSet7.setColor(Color.parseColor("#690ecc"));

                    LineDataSet lineDataSet8 = new LineDataSet(bmp, "BmP");
                    lineDataSet8.setDrawCircles(false);
                    lineDataSet8.setColor(Color.CYAN);

                    LineDataSet lineDataSet9 = new LineDataSet(alt, "Alt");
                    lineDataSet9.setDrawCircles(false);
                    lineDataSet9.setColor(Color.parseColor("#ff8c00"));

                    LineDataSet lineDataSet10 = new LineDataSet(rad, "Rad");
                    lineDataSet10.setDrawCircles(false);
                    lineDataSet10.setColor(Color.parseColor("#8e4f01"));

                    lineDataSets.add(lineDataSet1);
                    lineDataSets.add(lineDataSet2);
                    lineDataSets.add(lineDataSet3);
                    lineDataSets.add(lineDataSet4);
                    lineDataSets.add(lineDataSet5);
                    lineDataSets.add(lineDataSet6);
                    lineDataSets.add(lineDataSet7);
                    lineDataSets.add(lineDataSet8);
                    lineDataSets.add(lineDataSet9);
                    lineDataSets.add(lineDataSet10);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            if (lineDataSets != null && lineDataSets.size() > 0) {
                lineChart.setData(new LineData(lineDataSets));
                Description description = new Description();
                description.setText("Rover Sensor Data");
                lineChart.setDescription(description);
                lineChart.animateXY(1000, 1000, Easing.EasingOption.EaseInBack, Easing.EasingOption.EaseInBack);
            } else {
                Snackbar.make(findViewById(R.id.content_frame),"Not available sensor data!",Snackbar.LENGTH_LONG).show();
            }
            SwipeRefreshLayout srl = findViewById(R.id.swiperefresh);
            srl.setRefreshing(false);
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        ActionBar actionbar = getSupportActionBar();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        drawerOpened = sharedPref.getBoolean("drawer_opened",false);
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()){
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = ChartActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
    }
}
