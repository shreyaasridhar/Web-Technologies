package com.example.weather_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ip_api.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ip_api#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ip_api extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;

    private String mParam1;
    private String mParam2;
//    private RequestQueue http_queue;
//    protected JSONObject curlocation;
    protected JSONObject dark;
    protected JSONObject image;
    protected double lat;
    protected double lon;
    protected String city;
    protected String state;
    protected String country;
    protected String CompleteLocation;

    private OnFragmentInteractionListener mListener;

    public ip_api() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment ip_api.
     */
    public static ip_api newInstance(String param2, String param1) {
        ip_api fragment = new ip_api();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d("mparam1",mParam1);
        try {
            dark = new JSONObject(mParam2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        city = mParam1;

        //Log.d("in Oncreate of","ip_api");
//        http_queue = Volley.newRequestQueue(this.getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ip_api, container, false);
//        TextView temp = (TextView) view.findViewById(R.id.card1Temperature);
//        try {
//            temp.setText(dark.getJSONObject("darkskyapi").getJSONObject("currently").getString("temperature"));
//           } catch (JSONException e) {
//               e.printStackTrace();
//         }
//        Log.d("oncreate",String.valueOf(curlocation));
        CardView card = view.findViewById(R.id.card1);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailedinfo();

            }
        });

//        ip_apire();
        addVals();
        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
