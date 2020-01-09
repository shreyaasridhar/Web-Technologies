package com.example.weather_app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Favourite_switch extends FragmentPagerAdapter {
    protected Context mContext;
    public ArrayList<JSONObject> stored_location;
    private long baseId = 0;
    JSONObject dark;
    String city;
    public Favourite_switch(FragmentManager fm, Context context, ArrayList<JSONObject> storedcity) {
        super(fm);
        mContext = context;
        stored_location = storedcity;

        Log.d("Stored_location",String.valueOf(storedcity.size()));
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        dark = new JSONObject();
        try {
            dark = stored_location.get(i).getJSONObject("dark");
            city = stored_location.get(i).getString("City");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("itemval", i +city);

        if (i == 0){
            fragment = ip_api.newInstance(dark.toString(), city);
        }
        else{
            fragment = Cities_favourites.newInstance(dark.toString(),city,String.valueOf(i));
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return stored_location.size();
    }

    @Override
    public int getItemPosition(Object object) {
        Log.d("refresingadapter",object.toString());
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        // give an ID different from position when position has been changed
        return baseId + position;
    }
    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        Log.d("Changepos",String.valueOf(n));
        stored_location.remove(n);
        baseId += getCount() + n;
        notifyDataSetChanged();
    }
}
