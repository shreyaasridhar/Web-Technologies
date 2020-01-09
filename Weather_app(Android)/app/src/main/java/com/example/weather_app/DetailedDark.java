package com.example.weather_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather_app.ui.main.SectionsPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailedDark extends AppCompatActivity {
//    double lat,lon;
    String dark_obj,image_obj,city;
    JSONObject dark;
    TabLayout tabs;
    protected RequestQueue volley_http_queue;
    protected JSONObject image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_dark);
        volley_http_queue = Volley.newRequestQueue(this.getBaseContext());
        Intent intent = getIntent();
        dark_obj = intent.getStringExtra("Darksky_val");
        city = intent.getStringExtra("City");
        getSupportActionBar().setTitle(city);
        try {
            dark = new JSONObject(dark_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fetch_image();

    }

    public void fetch_image(){
        JSONObject city_val = new JSONObject();
        try{
            city_val.put("city",city.substring(0,city.indexOf(',')));
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
                            Log.d("Image_obtained", response.getString("kind"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setupAdapter();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        volley_http_queue.add(request);

    }
    private void setupAdapter(){
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getBaseContext(), getSupportFragmentManager(), dark_obj, image.toString());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(R.drawable.calendar_today);
        tabs.getTabAt(1).setIcon(R.drawable.trending_up);
        tabs.getTabAt(2).setIcon(R.drawable.google_photos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailsmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.twitter:
                    String twitter_str = null;
                    try {
                        twitter_str = "Check out "+ city + "'s weather! It is "+ dark.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("temperature")+"°F! %23CSCI571WeatherSearch";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String urltwitter = "https://twitter.com/intent/tweet?text=" + twitter_str;
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urltwitter));//"android.intent.action.VIEW"
                    startActivity(viewIntent);
            }
        return super.onOptionsItemSelected(item);
    }

}