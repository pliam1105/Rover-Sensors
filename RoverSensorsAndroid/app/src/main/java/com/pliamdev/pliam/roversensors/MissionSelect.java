package com.pliamdev.pliam.roversensors;

import android.app.SearchManager;
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
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MissionSelect extends AppCompatActivity {
    public ArrayList<String> mission_start;
    public ArrayList<String> planet;
    public ArrayList<String> missions;
    public ListView mission_list;
    public ArrayAdapter<String> mission_adapter;
    public SearchManager searchManager;
    public MenuItem searchMenuItem;
    public SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_select);
        Toolbar toolbar = findViewById(R.id.toolbar_mission_select);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setTitle("Select Mission");
        }
        mission_list=findViewById(R.id.mission_list);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()){
            if(actionbar != null){
                actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            }
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = MissionSelect.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }

        final SwipeRefreshLayout srl = findViewById(R.id.swiperefresh_mission_select);
        srl.setRefreshing(true);
        new ParseMission().execute();
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
                //close search view if it is visible
                if(searchView.isShown()){
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                String[] missionString = Objects.requireNonNull(sharedPref.getString("missions", null)).split(",");
                Object itemName = mission_list.getItemAtPosition(position);
                String keyword = itemName.toString();
                int index = -1;
                for (int i = 0; i < missionString.length; i++){
                    if(missionString[i].equals(keyword)){
                        index = i;
                    }
                }
                if(index>=0){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("mission_id",index+1);
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }else{
                    Snackbar.make(findViewById(R.id.mission_select_coordinator),"Unfortunately, an error has been encountered!",Snackbar.LENGTH_LONG).show();
                }

            }
        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ParseMission().execute();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        mission_list.setTextFilterEnabled(true);

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint(getString(R.string.search_mission));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    mission_list.clearTextFilter();
                }else{
                    mission_list.setFilterText(newText);
                }

                return true;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED,returnIntent);
        finish();
    }

    public class ParseMission extends AsyncTask<String, String, String> {

        final String TAG = "ParseMission.java";
        // set your json string url here
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        String yourJsonStringUrl = "https://"+sharedPref.getString("host","pliamprojects.000webhostapp.com/rover")+"/v_planet_mission_json.php";

        // contacts JSONArray
        JSONArray dataJsonArr = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                mission_start=new ArrayList<>();
                missions=new ArrayList<>();
                planet=new ArrayList<>();
                // instantiate our json parser
                JsonParser jParser = new JsonParser();

                // get the array of data
                dataJsonArr = jParser.getJSONFromUrl(yourJsonStringUrl);
                if (dataJsonArr != null && dataJsonArr.length() > 0) {
                    // loop through all data
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < dataJsonArr.length(); i++) {
                        JSONObject c = dataJsonArr.getJSONObject(i);
                        mission_start.add(c.getString("start_time"));
                        planet.add(c.getString("Name"));
                        missions.add("Start: "+mission_start.get(i)+" Planet: "+planet.get(i));
                        sb.append("Start: ").append(mission_start.get(i)).append(" Planet: ").append(planet.get(i)).append(",");

                    }

                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("missions",sb.toString());
                    editor.apply();
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
            SwipeRefreshLayout srl = findViewById(R.id.swiperefresh_mission_select);
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
            Window window = MissionSelect.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
    }
}
