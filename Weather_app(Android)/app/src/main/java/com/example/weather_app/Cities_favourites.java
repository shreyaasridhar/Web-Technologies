package com.example.weather_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Cities_favourites.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Cities_favourites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cities_favourites extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "Cities_favourites";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private String mParam2;
    private String mParam3;
    View view;
    protected JSONObject dark,image;
    protected double lat;
    protected double lon;
    protected String city;
    JSONObject unpacked_localstorage;
    private RequestQueue http_queue;
    FloatingActionButton fab;
    boolean flag;
    SharedPreferences sharedPreferences;

    private OnFragmentInteractionListener mListener;
    private Set<String> StoredData;

    public Cities_favourites() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cities_favourites.
     */
    public static Cities_favourites newInstance(String param2, String c,String param1) {
        Cities_favourites fragment = new Cities_favourites();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM3, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM1, c);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
        try {
            dark = new JSONObject(mParam2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        city = mParam1;

//        http_queue = Volley.newRequestQueue(this.getContext());
//        try {
//            unpacked_localstorage = new JSONObject(mParam2);
//            city = unpacked_localstorage.getString("City");
//            lat = unpacked_localstorage.getDouble("lat");
//            lon = unpacked_localstorage.getDouble("long");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cities_favourites, container, false);

        addVals();
        CardView card = view.findViewById(R.id.card1);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailedinfo();
            }
        });

        fab = view.findViewById(R.id.fab);
        sharedPreferences = getActivity().getSharedPreferences("myFavourites", Context.MODE_PRIVATE);
        Set<String> CheckVal = sharedPreferences.getStringSet("location",null);
        JSONObject unpacked_localstorage;
        flag = false;
        if (CheckVal != null){
            for(String s: CheckVal){
                try {
                    unpacked_localstorage = new JSONObject(s);
                    if (city.toLowerCase().equals(unpacked_localstorage.getString("City").toLowerCase())){
                        flag=true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (flag == true)
            fab.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.map_marker_minus));
        if (flag == false)
            fab.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.map_marker_plus));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removefromFav();

            }
        });
        return view;
    }
    public void removefromFav(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StoredData = new HashSet<String>();
        JSONObject store_val = new JSONObject();
        try{
            store_val.put("City",city);
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
        if (retrieveVal!=null){
            Log.d(TAG, "Removing"+city+"from Fav");
            Toast.makeText(getContext(),city +" was removed from favorites",Toast.LENGTH_SHORT).show();
            StoredData.remove(store_val.toString());
        }
        retrieveVal = sharedPreferences.getStringSet("location",null);
        Log.d(TAG, "afterbuttonclick : "+retrieveVal);
        editor.putStringSet("location",StoredData);
        editor.commit();
        ((MainActivity)getActivity()).FavoriteAdapter.notifyChangeInPosition(Integer.valueOf(mParam3));
    }
    public void openDetailedinfo(){

        Intent intent = new Intent(getActivity(), DetailedDark.class);
        intent.putExtra("Darksky_val",String.valueOf(dark));
        intent.putExtra("City",city);
        intent.putExtra("Image_val",String.valueOf(image));
        startActivity(intent);

    }
    public void addVals(){
        TextView temp = view.findViewById(R.id.card1Temperature);
        TextView summary = view.findViewById(R.id.card1Summary);
        ImageView iconview = view.findViewById(R.id.card1Icon);
        TextView city_card_val = view.findViewById(R.id.City);

        try {
            String dark_icon = dark.getJSONObject("darkskyapi").getJSONObject("currently").getString("icon");
            temp.setText((int) dark.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("temperature") +"°F");
            summary.setText(dark.getJSONObject("darkskyapi").getJSONObject("currently").getString("summary"));
            darkiconmap(iconview,dark_icon);
            city_card_val.setText(city);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView humidity = view.findViewById(R.id.humiditycard2);
        TextView windspeed = view.findViewById(R.id.card2windspeed);
        TextView visibility = view.findViewById(R.id.card2visibility);
        TextView pressure = view.findViewById(R.id.card2pressure);

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
            int id = getResources().getIdentifier("table" + (i + 1) + 1, "id", getActivity().getPackageName());
            Time_val = view.findViewById(id);
            Time_val.setText(new SimpleDateFormat("MM/dd/yyyy").format(time_data[i] * 1000));
            id = getResources().getIdentifier("table" + (i + 1) + 2, "id", getActivity().getPackageName());
            icon_val = view.findViewById(id);
            darkiconmap(icon_val,icons_overview[i]);
            id = getResources().getIdentifier("table" + (i + 1) + 3, "id", getActivity().getPackageName());
            tLow = view.findViewById(id);
            tLow.setText(String.valueOf(temp_lows[i]));
            id = getResources().getIdentifier("table" + (i + 1) + 4, "id", getActivity().getPackageName());
            tHigh = view.findViewById(id);
            tHigh.setText(String.valueOf(temp_highs[i]));
        }
    }
    public void darkiconmap(ImageView iconview, String str_icon){
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
