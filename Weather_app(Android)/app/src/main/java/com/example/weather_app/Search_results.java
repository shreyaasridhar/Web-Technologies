package com.example.weather_app;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class Search_results extends AppCompatActivity {
    private static final String TAG = "Search_results";
    String city_rec;
    JSONObject dark,image;
    private ProgressBar spinner;
    private TextView fetch;
    private CardView card1;
    private CardView card2;
    private CardView card3;
    private TextView textView;
    boolean flag;
    private RequestQueue http_queue;
    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
    protected HashSet<String> StoredData;
    FloatingActionButton fab;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });
        http_queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            city_rec = intent.getStringExtra(SearchManager.QUERY);
        }        darksky_call();
        getSupportActionBar().setTitle(city_rec);
        sharedPreferences = getSharedPreferences("myFavourites", Context.MODE_PRIVATE);
        Set<String> CheckVal = sharedPreferences.getStringSet("location",null);
        JSONObject unpacked_localstorage;
        flag = false;
        if (CheckVal != null){
            for(String s: CheckVal){
                try {
                    unpacked_localstorage = new JSONObject(s);
                    if (city_rec.toLowerCase().equals(unpacked_localstorage.getString("City").toLowerCase())){
                        flag=true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }




        spinner = findViewById(R.id.progressBar1);
        fetch = findViewById(R.id.fetchtext);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        textView = findViewById(R.id.textView);
        fab = findViewById(R.id.fab);
        fetch.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        card1.setVisibility(View.GONE);
        card2.setVisibility(View.GONE);
        card3.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                spinner.setVisibility(View.GONE);
                fetch.setVisibility(View.GONE);
                card1.setVisibility(View.VISIBLE);
                card2.setVisibility(View.VISIBLE);
                card3.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);

                if (flag == true)
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.map_marker_minus));
                if (flag == false)
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.map_marker_plus));
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addtoFav();

                    }
                });

                CardView card = findViewById(R.id.card1);
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ewqre","OncreateView");
                        openDetailedinfo();

                    }
                });
            }
        },600);




    }

    public void addtoFav(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StoredData = new HashSet<String>();
        JSONObject store_val = new JSONObject();
        try{
            store_val.put("City",city_rec);
            store_val.put("lat",dark.getJSONObject("darkskyapi").getDouble("latitude"));
            store_val.put("long",dark.getJSONObject("darkskyapi").getDouble("longitude"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Set<String> retrieveVal = sharedPreferences.getStringSet("location",null);
        Log.d(TAG, "RetriveVal beforeclick: "+retrieveVal);
        if (retrieveVal != null){
            for(String s: retrieveVal){
                StoredData.add(s);
            }
        }
        if (retrieveVal!=null && retrieveVal.contains(store_val.toString())){
            Log.d(TAG, "Removing"+city_rec+"from Fav");
            Toast.makeText(getApplicationContext(),city_rec +" was removed from favorites",Toast.LENGTH_SHORT).show();
            StoredData.remove(store_val.toString());
            flag = false;
            fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.map_marker_plus));

        } else{
            Toast.makeText(getApplicationContext(),city_rec +" was added to favorites",Toast.LENGTH_SHORT).show();
            StoredData.add(store_val.toString());
            flag = false;
            fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.map_marker_minus));
        }
        retrieveVal = sharedPreferences.getStringSet("location",null);
        Log.d(TAG, "afterbuttonclick : "+retrieveVal);
        editor.putStringSet("location",StoredData);
        editor.commit();

    }
    public void openDetailedinfo(){
        Log.d(TAG, "openDetailedinfo: ");
        Intent intent = new Intent(this, DetailedDark.class);
        intent.putExtra("Darksky_val",String.valueOf(dark));
        intent.putExtra("Image_val",String.valueOf(image));
        intent.putExtra("City",city_rec);
        startActivity(intent);

    }
    public void darksky_call(){
        JSONObject json_Vals = new JSONObject();
        try{
            json_Vals.put("City",city_rec);
            json_Vals.put("State","");
            json_Vals.put("Street","");
            json_Vals.put("CurLoc",false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jsonvals",json_Vals.toString());
        String val_URL = "http://"+getString(R.string.ip_address)+"/currentweather";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, val_URL, json_Vals,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dark = response;
                        try {
                            Log.d("SearchResultsdarkresponse",response.toString());
                            Log.d(TAG, response.getJSONObject("darkskyapi").getString("timezone")); //TODO check status code
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        addVals();
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
    public void fetch_image(){
        JSONObject city_val = new JSONObject();
        try{
            city_val.put("city",city_rec.split(",")[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("city_val",city_val.toString());
        String val_URL = "http://"+getString(R.string.ip_address)+"/images";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, val_URL, city_val,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        image = response;
                        try {
                            Log.d(TAG, response.getString("kind"));
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
    public void addVals(){
        TextView temp = findViewById(R.id.card1Temperature);
        TextView summary = findViewById(R.id.card1Summary);
        ImageView iconview = findViewById(R.id.card1Icon);
        TextView city_card_val = findViewById(R.id.City);

        try {
            String dark_icon = dark.getJSONObject("darkskyapi").getJSONObject("currently").getString("icon");
            temp.setText((int) dark.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("temperature") +"°F");
            summary.setText(dark.getJSONObject("darkskyapi").getJSONObject("currently").getString("summary"));
            darkiconmap(iconview,dark_icon);
            city_card_val.setText(city_rec);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView humidity = findViewById(R.id.humiditycard2);
        TextView windspeed = findViewById(R.id.card2windspeed);
        TextView visibility = findViewById(R.id.card2visibility);
        TextView pressure = findViewById(R.id.card2pressure);

        try {
            windspeed.setText(String.format("%.2f", dark.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("windSpeed"))+"mph");
            humidity.setText(String.format("%.0f",dark.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("humidity")*100)+"%");
            visibility.setText(String.format("%.2f",dark.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("visibility"))+"km");
            pressure.setText(String.format("%.2f",dark.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("pressure"))+"mb");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] temp_highs = new String[8];
        String[] temp_lows = new String[8];
        long[] time_data = new long[8];
        String[] icons_overview = new String[8];
        try {
            JSONArray data_array = dark.getJSONObject("darkskyapi").getJSONObject("daily").getJSONArray("data");
            Log.d("Weeklydata",data_array.toString());
            for (int i = 0; i < 8; i++) {
                time_data[i] = data_array.getJSONObject(i).getLong("time");
                temp_highs[i] = String.format("%.0f",data_array.getJSONObject(i).getDouble("temperatureHigh"));
                temp_lows[i] = String.format("%.0f",data_array.getJSONObject(i).getDouble("temperatureLow"));
                icons_overview[i] = data_array.getJSONObject(i).getString("icon");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView Time_val,tHigh,tLow;
        ImageView icon_val;
        for (int i=0;i<8;i++){
            int id = getResources().getIdentifier("table" + (i + 1) + 1, "id", getPackageName());
            Time_val = findViewById(id);
            Time_val.setText(new SimpleDateFormat("MM/dd/yyyy").format(time_data[i] * 1000));
            id = getResources().getIdentifier("table" + (i + 1) + 2, "id", getPackageName());
            icon_val = findViewById(id);
            darkiconmap(icon_val,icons_overview[i]);
            id = getResources().getIdentifier("table" + (i + 1) + 3, "id", getPackageName());
            tLow = findViewById(id);
            tLow.setText(String.valueOf(temp_lows[i]));
            id = getResources().getIdentifier("table" + (i + 1) + 4, "id", getPackageName());
            tHigh = findViewById(id);
            tHigh.setText(String.valueOf(temp_highs[i]));
        }
    }
    public void darkiconmap(ImageView iconview,String str_icon){
        if (str_icon.equals("clear-night")){
            iconview.setImageResource(R.drawable.weather_night); }
        else if (str_icon.equals("rain") ){
            iconview.setImageResource(R.drawable.weather_rainy);}
        else if (str_icon.equals("snow")){
            iconview.setImageResource(R.drawable.weather_snowy);}
        else if (str_icon.equals("sleet")){
            iconview.setImageResource(R.drawable.weather_snowy_rainy);}
        else if (str_icon.equals("wind")){
            iconview.setImageResource(R.drawable.weatherwindyvariant);}
        else if (str_icon.equals("fog")){
            iconview.setImageResource(R.drawable.weatherfogdark);}
        else if (str_icon.equals("cloudy")){
            iconview.setImageResource(R.drawable.weather_cloudy);}
        else if (str_icon.equals("partly-cloudy-day")){
            iconview.setImageResource(R.drawable.weather_partly_cloudy);}
        else if (str_icon.equals("partly-cloudy-night")){
            iconview.setImageResource(R.drawable.weather_night_partly_cloudy);}
        else{
            iconview.setImageResource(R.drawable.weather_sunny); }
    }

}
