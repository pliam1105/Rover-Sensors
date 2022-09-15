package com.pliamdev.pliam.roversensors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.browser.customtabs.CustomTabsIntent;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlanetActivity extends AppCompatActivity {

    public boolean planet_habitability;
    public String planet_name;
    public String planet_type;
    public String planet_mass;
    public String planet_radius;
    public String planet_flux;
    public String planet_teq;
    public String planet_period;
    public String planet_distance;
    public String planet_esi;
    public String mission_id;
    public SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);
        Toolbar toolbar = findViewById(R.id.toolbar_planet);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Planet Details");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()){
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            FloatingActionButton fab = findViewById(R.id.fav_fab);
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            Button planet_button = findViewById(R.id.planetButton);
            Button type_button = findViewById(R.id.typeButton);
            Button mass_button = findViewById(R.id.massButton);
            Button radius_button = findViewById(R.id.radiusButton);
            Button flux_button = findViewById(R.id.fluxButton);
            Button teq_button = findViewById(R.id.teqButton);
            Button period_button = findViewById(R.id.periodButton);
            Button distance_button = findViewById(R.id.distanceButton);
            Button esi_button = findViewById(R.id.esiButton);
            Button habit_button = findViewById(R.id.planetHabitButton);
            planet_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            type_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            mass_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            radius_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            flux_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            teq_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            period_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            distance_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            esi_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            habit_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = PlanetActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
        FloatingActionButton fav_fab = findViewById(R.id.fav_fab);
        fav_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                if(sharedPref.getBoolean(sharedPref.getString("planet_name", null),false)){
                    editor.putBoolean(sharedPref.getString("planet_name", null),false);
                    editor.apply();
                    FloatingActionButton fav_fab = findViewById(R.id.fav_fab);
                    fav_fab.setImageResource(R.drawable.ic_star_white);
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.planet_coordinator),"Removed from favorites",Snackbar.LENGTH_LONG);
                    snackbar.setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean(sharedPref.getString("planet_name", null),true);
                            editor.apply();
                            FloatingActionButton fav_fab = findViewById(R.id.fav_fab);
                            fav_fab.setImageResource(R.drawable.ic_star);
                        }
                    });
                    snackbar.show();
                }else{
                    editor.putBoolean(sharedPref.getString("planet_name", null),true);
                    editor.apply();
                    FloatingActionButton fav_fab = findViewById(R.id.fav_fab);
                    fav_fab.setImageResource(R.drawable.ic_star);
                    Snackbar.make(findViewById(R.id.planet_coordinator),"Added to favorites",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        AppBarLayout planet_details_appbar_layout = findViewById(R.id.planet_details_appbar_layout);
        planet_details_appbar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                SwipeRefreshLayout srl = findViewById(R.id.swiperefresh_planet);
                srl.setEnabled(verticalOffset == 0);
            }
        });
        Intent i = getIntent();
        mission_id = i.getStringExtra("mission_id");
        final SwipeRefreshLayout srl = findViewById(R.id.swiperefresh_planet);
        srl.setRefreshing(true);
        new ParseMission().execute(mission_id);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ParseMission().execute(mission_id);
            }
        });
        Button planet_button = findViewById(R.id.planetButton);
        planet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                // and launch the desired Url with CustomTabsIntent.launchUrl()

                String url = "https://en.wikipedia.org/wiki/Planet";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(PlanetActivity.this, Uri.parse(url));
            }
        });
        Button type_button = findViewById(R.id.typeButton);
        type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                // and launch the desired Url with CustomTabsIntent.launchUrl()

                String url = "https://en.wikipedia.org/wiki/List_of_planet_types";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(PlanetActivity.this, Uri.parse(url));
            }
        });
        Button mass_button = findViewById(R.id.massButton);
        mass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                // and launch the desired Url with CustomTabsIntent.launchUrl()

                String url = "https://en.wikipedia.org/wiki/Mass";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(PlanetActivity.this, Uri.parse(url));
            }
        });
        Button radius_button = findViewById(R.id.radiusButton);
        radius_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                // and launch the desired Url with CustomTabsIntent.launchUrl()

                String url = "https://en.wikipedia.org/wiki/Radius";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(PlanetActivity.this, Uri.parse(url));
            }
        });
        Button flux_button = findViewById(R.id.fluxButton);
        flux_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                // and launch the desired Url with CustomTabsIntent.launchUrl()

                String url = "https://en.wikipedia.org/wiki/Flux";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(PlanetActivity.this, Uri.parse(url));
            }
        });
        Button teq_button = findViewById(R.id.teqButton);
        teq_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                // and launch the desired Url with CustomTabsIntent.launchUrl()

                String url = "https://en.wikipedia.org/wiki/Planetary_equilibrium_temperature";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(PlanetActivity.this, Uri.parse(url));
            }
        });
        Button period_button = findViewById(R.id.periodButton);
        period_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                // and launch the desired Url with CustomTabsIntent.launchUrl()

                String url = "https://en.wikipedia.org/wiki/Orbital_period";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(PlanetActivity.this, Uri.parse(url));
            }
        });
        Button distance_button = findViewById(R.id.distanceButton);
        distance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                // and launch the desired Url with CustomTabsIntent.launchUrl()

                String url = "https://en.wikipedia.org/wiki/Distance";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(PlanetActivity.this, Uri.parse(url));
            }
        });
        Button esi_button = findViewById(R.id.esiButton);
        esi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                // and launch the desired Url with CustomTabsIntent.launchUrl()

                String url = "https://en.wikipedia.org/wiki/Earth_Similarity_Index";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(PlanetActivity.this, Uri.parse(url));
            }
        });
        Button habit_button = findViewById(R.id.planetHabitButton);
        habit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                // and lunch the desired Url with CustomTabsIntent.launchUrl()

                String url = "https://en.wikipedia.org/wiki/Planetary_habitability";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(PlanetActivity.this, Uri.parse(url));
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public class ParseMission extends AsyncTask<String, String, String> {

        final String TAG = "ParsePlanet.java";

        // planet JSONArray
        JSONArray dataJsonArr = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                // instantiate our json parser
                JsonParser jParser = new JsonParser();
                // set your json string url here
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                String yourJsonStringUrl = "https://"+sharedPref.getString("host","pliamprojects.000webhostapp.com/rover")+"/v_planet_mission_id_json.php?mission_id=" + arg0[0];
                // get the array of data
                dataJsonArr = jParser.getJSONFromUrl(yourJsonStringUrl);
                if (dataJsonArr != null && dataJsonArr.length() > 0) {
                    // loop through all data
                    for (int i = 0; i < dataJsonArr.length(); i++) {
                        JSONObject c = dataJsonArr.getJSONObject(i);
                        planet_name = c.getString("Name");
                        planet_type = c.getString("Type");
                        planet_mass = c.getString("Mass (ME)");
                        planet_radius = c.getString("Radius (RE)");
                        planet_flux = c.getString("Flux (SE)");
                        planet_teq = c.getString("Teq (K)");
                        planet_period = c.getString("Period (days)");
                        planet_distance = c.getString("Distance(ly)");
                        planet_esi = c.getString("ESI");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
            String habitable_mission_url = "https://"+sharedPref.getString("host","pliamprojects.000webhostapp.com/rover")+"/habitable_mission.php?mission_id=" + arg0[0];
            String response = "";
            try {
                URL uri = new URL(habitable_mission_url);
                HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
                InputStream content = null;
                try{
                    content = new BufferedInputStream(urlConnection.getInputStream());
                }finally{
                    urlConnection.disconnect();
                }
                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String response_html= Html.fromHtml(response).toString();
            if(response_html.equals("1")){
                planet_habitability=true;
            }else{
                planet_habitability=false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            if(planet_name!=null) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("planet_name",planet_name);
                editor.apply();
                if(sharedPref.getBoolean(planet_name,false)){
                    FloatingActionButton fav_fab = findViewById(R.id.fav_fab);
                    fav_fab.setImageResource(R.drawable.ic_star);
                }else{
                    FloatingActionButton fav_fab = findViewById(R.id.fav_fab);
                    fav_fab.setImageResource(R.drawable.ic_star_white);
                }
                ImageView planet_image = findViewById(R.id.planet_image);
                switch (planet_name) {
                    case "Proxima Cen b":
                        planet_image.setImageResource(R.drawable.proxima_cen_b);
                        break;
                    case "TRAPPIST-1 e":
                        planet_image.setImageResource(R.drawable.trappist_1_e);
                        break;
                    case "GJ 667 C c":
                        planet_image.setImageResource(R.drawable.gj_667_c_c);
                        break;
                    case "Kepler-442 b":
                        planet_image.setImageResource(R.drawable.kepler_442b);
                        break;
                    case "GJ 667 C f":
                        planet_image.setImageResource(R.drawable.gj_667_c_f);
                        break;
                    case "Kepler-1229 b":
                        planet_image.setImageResource(R.drawable.kepler_1229b);
                        break;
                    case "TRAPPIST-1 f":
                        planet_image.setImageResource(R.drawable.trappist_1f);
                        break;
                    case "LHS 1140 b":
                        planet_image.setImageResource(R.drawable.lhs_1140b);
                        break;
                    case "Kapteyn b":
                        planet_image.setImageResource(R.drawable.kapteyn_b);
                        break;
                    case "Kepler-62 f":
                        planet_image.setImageResource(R.drawable.kepler_62f);
                        break;
                    case "Kepler-186 f":
                        planet_image.setImageResource(R.drawable.kepler_186f);
                        break;
                    case "GJ 667 C e":
                        planet_image.setImageResource(R.drawable.gj_667_c_e);
                        break;
                    case "TRAPPIST-1 g":
                        planet_image.setImageResource(R.drawable.trappist_1_g);
                        break;
                }
                CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
                collapsingToolbarLayout.setTitle(planet_name);
                TextView name = findViewById(R.id.planetText);
                TextView type = findViewById(R.id.typeText);
                TextView mass = findViewById(R.id.massText);
                TextView radius = findViewById(R.id.radiusText);
                TextView flux = findViewById(R.id.fluxText);
                TextView teq = findViewById(R.id.teqText);
                TextView period = findViewById(R.id.periodText);
                TextView distance = findViewById(R.id.distanceText);
                TextView esi = findViewById(R.id.esiText);
                TextView habitability = findViewById(R.id.planetHabitText);
                name.setText(planet_name);
                type.setText(planet_type);
                mass.setText(String.format("%s ME", planet_mass));
                radius.setText(String.format("%s RE", planet_radius));
                flux.setText(String.format("%s SE", planet_flux));
                teq.setText(String.format("%s K", planet_teq));
                period.setText(String.format("%s days", planet_period));
                distance.setText(String.format("%s ly", planet_distance));
                esi.setText(planet_esi);
                if(planet_habitability){
                    habitability.setText("Planet is habitable!");
                }else{
                    habitability.setText("Planet is inhabitable!");
                }
            }else{
                Snackbar.make(findViewById(R.id.planet_coordinator),"Not available planet data!",Snackbar.LENGTH_LONG).show();
            }
            SwipeRefreshLayout srl = findViewById(R.id.swiperefresh_planet);
            srl.setRefreshing(false);
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(!sharedPref.getString("primaryColor","#0000ff").isEmpty()){
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            FloatingActionButton fab = findViewById(R.id.fav_fab);
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sharedPref.getString("primaryColor","#0000ff"))));
            Button planet_button = findViewById(R.id.planetButton);
            Button type_button = findViewById(R.id.typeButton);
            Button mass_button = findViewById(R.id.massButton);
            Button radius_button = findViewById(R.id.radiusButton);
            Button flux_button = findViewById(R.id.fluxButton);
            Button teq_button = findViewById(R.id.teqButton);
            Button period_button = findViewById(R.id.periodButton);
            Button distance_button = findViewById(R.id.distanceButton);
            Button esi_button = findViewById(R.id.esiButton);
            Button habit_button = findViewById(R.id.planetHabitButton);
            planet_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            type_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            mass_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            radius_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            flux_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            teq_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            period_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            distance_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            esi_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
            habit_button.setBackgroundColor(Color.parseColor(sharedPref.getString("primaryColor","#0000ff")));
        }
        if(!sharedPref.getString("primaryColorDark","#0000f0").isEmpty()) {
            Window window = PlanetActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(sharedPref.getString("primaryColorDark","#0000f0")));
        }
    }
}
