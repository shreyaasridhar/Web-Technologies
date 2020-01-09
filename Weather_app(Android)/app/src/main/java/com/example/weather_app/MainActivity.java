package com.example.weather_app;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    Favourite_switch FavoriteAdapter;
    private ProgressBar spinner;
    private TextView fetch;
    ArrayAdapter<String> newsAdapter;
    ArrayList<String> drop_down;
    SearchView.SearchAutoComplete searchauto;
    protected RequestQueue http_queue;
    JSONArray predictions;

//    private RequestQueue http_queue;
    protected JSONObject curlocation;
    protected JSONObject dark;
    protected ArrayList<JSONObject> dark_array;
    protected JSONObject image;
    protected double lat;
    protected double lon;
    protected String city;
    protected String state;
    protected String country;
    protected String CompleteLocation;
    Integer counter,Num_frags;
    Set<String> retrieveVal;

    Set<String> StoredData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = 0;
        http_queue = Volley.newRequestQueue(this);
        spinner = findViewById(R.id.progressBar1);
        fetch = findViewById(R.id.fetchtext);
        spinner.setVisibility(View.VISIBLE);
        fetch.setVisibility(View.VISIBLE);
        ip_apire();
        sharedpreferences = getSharedPreferences("myFavourites", Context.MODE_PRIVATE);
        retrieveVal = sharedpreferences.getStringSet("location",null);
        Num_frags = 1;
        if (retrieveVal != null){
        Num_frags = retrieveVal.size()+1;
        Log.d("frag",String.valueOf(Num_frags));
        }
        StoredData = new HashSet<String>();
        dark_array = new ArrayList<JSONObject>();

    }
    public void ip_apire(){
        String url ="http://ip-api.com/json";
        //Log.d("ipapistringurl", url);
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("ipapistring", response.toString());
                            curlocation = response;
                            double lat_ip = curlocation.getDouble("lat");
                            double lon_ip = curlocation.getDouble("lon");
                            city = curlocation.getString("city"); //TODO add the whole address
                            state = curlocation.getString("region");
                            country = curlocation.getString("countryCode");
                            city = city + ", " + state + ", " + country;
                            darksky_call_latlong(lat_ip, lon_ip, city);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (retrieveVal != null){
                            for(String s: retrieveVal){
                                StoredData.add(s);
                                try {
                                    JSONObject unpacked_localstorage = new JSONObject(s);
                                    String city = unpacked_localstorage.getString("City");
                                    double lat = unpacked_localstorage.getDouble("lat");
                                    double lon = unpacked_localstorage.getDouble("long");
                                    darksky_call_latlong(lat,lon, city);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        http_queue.add(request);
    }
    public void darksky_call_latlong(double lat, double lon, final String city_val){
        JSONObject json_Vals = new JSONObject();
        try{
            json_Vals.put("lat",lat);
            json_Vals.put("lon",lon);
            json_Vals.put("CurLoc",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d("jsonvals",json_Vals.toString());
        String val_URL = "http://"+getString(R.string.ip_address)+"/currentweather";
        //Log.d("urlipapi",val_URL);
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, val_URL, json_Vals,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dark = response;
                        JSONObject fav = new JSONObject();
                        try{
                            fav.put("dark",dark);
                            fav.put("City",city_val);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dark_array.add(fav);
//                        addVals();
                        try {
                            Log.d("response_obtained", response.getJSONObject("darkskyapi").getString("timezone")); //TODO check status code
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setupAdapter();
                        //fetch_image();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        http_queue.add(request);
    }
    public void setupAdapter(){
        counter+=1;
        Log.d("counter",String.valueOf(counter));
        if (counter == (Num_frags )) {
            spinner.setVisibility(View.GONE);
            fetch.setVisibility(View.GONE);
            FavoriteAdapter = new Favourite_switch(this.getSupportFragmentManager(), this, dark_array);
            Log.d("Dark_array", dark_array.toString());
            Log.d("Dark_array", String.valueOf(dark_array.size()));
            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(FavoriteAdapter);
            TabLayout tabs = findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint("Search...");
        ComponentName searchactivity = new ComponentName(getBaseContext(), Search_results.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(searchactivity));

        searchauto = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchauto.setBackgroundColor(getColor(R.color.colorPrimary));
        searchauto.setTextColor(getColor(R.color.whitecolor));
        searchauto.setDropDownBackgroundResource(R.color.whitecolor);

//        newsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, drop_down);
//        searchauto.setAdapter(newsAdapter);
        searchauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchauto.setText("" + queryString);
//                Toast.makeText(MainActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Log.d("onQueryTextSubmit: ",query);
                drop_down = new ArrayList<String>();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("onQueryTextChange: ",newText);
//                Pattern p = Pattern.compile("^[ A-Za-z]+$");
//                Matcher m = p.matcher(newText);
//                if (m.matches() == true)
                Autocomplete_api(newText);
                return false;
            }
        });
        return true;
    }
    public void Autocomplete_api(String newText){
        JSONObject json_Vals = new JSONObject();
        try{
            json_Vals.put("city",newText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jsonvals",json_Vals.toString());
        String val_URL = "http://"+getString(R.string.ip_address)+"/autocomplete";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, val_URL, json_Vals,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        drop_down = new ArrayList<String>();
                        try {
                            predictions = response.getJSONArray("predictions");
                            for(int i=0; i<predictions.length(); i++){
                                drop_down.add(predictions.getJSONObject(i).getString("description"));
                            }
                            Log.d("testdropdown",drop_down.toString());
//                            Log.d("predictionsarray",predictions.getJSONObject(0).getString("description"));
                            newsAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, drop_down);
                            searchauto.setAdapter(newsAdapter);
                            newsAdapter.notifyDataSetChanged();
//                            newsAdapter.getFilter().filter(searchauto.getText(),null);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        http_queue.add(request);
    }

}

