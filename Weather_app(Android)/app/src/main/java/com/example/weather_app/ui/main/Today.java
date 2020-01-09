package com.example.weather_app.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather_app.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Today.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Today#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Today extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    protected View view;
    JSONObject dark_api;

    private OnFragmentInteractionListener mListener;

    public Today() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Today.
     */
    // TODO: Rename and change types and number of parameters
    public static Today newInstance(String param1) {
        Today fragment = new Today();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        try {
            dark_api = new JSONObject(mParam1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_today, container, false);
        appVals();
        return view;
    }

    private void appVals() {
        TextView windspeed = view.findViewById(R.id.Windspeed);
        TextView pressure = view.findViewById(R.id.Pressure);
        TextView precep = view.findViewById(R.id.Precipitation);
        TextView temper = view.findViewById(R.id.Temperature);
        TextView summary = view.findViewById(R.id.todaySummary);
        TextView humidity = view.findViewById(R.id.Humidity);
        TextView visibility = view.findViewById(R.id.Visibility);
        TextView cloudcover = view.findViewById(R.id.CloudCover);
        TextView ozone = view.findViewById(R.id.Ozone);

        ImageView iconview = view.findViewById(R.id.todayIcon);

        try {
            String dark_icon = dark_api.getJSONObject("darkskyapi").getJSONObject("currently").getString("icon");
            windspeed.setText(String.format("%.2f", dark_api.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("windSpeed"))+" mph");
            humidity.setText(String.format("%.0f",dark_api.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("humidity")*100)+"%");
            visibility.setText(String.format("%.2f",dark_api.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("visibility"))+" km");
            pressure.setText(String.format("%.2f",dark_api.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("pressure"))+" mb");
            temper.setText((int) dark_api.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("temperature") +"°F");
            precep.setText(String.format("%.2f",dark_api.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("precipIntensity"))+" mmph");
            cloudcover.setText(String.format("%.0f",dark_api.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("cloudCover")*100)+"%");
            ozone.setText(String.format("%.2f",dark_api.getJSONObject("darkskyapi").getJSONObject("currently").getDouble("ozone"))+" DU");
            if (iconview.equals("partly-cloudy-day")){
                summary.setText("cloudy day");}
            else if (iconview.equals("partly-cloudy-night")){
                summary.setText("cloudy night");
            } else {
                summary.setText(dark_icon.replace("-"," "));//TODO replace partly and - with space
            }
            darkiconmap(iconview,dark_icon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void darkiconmap(ImageView iconview, String str_icon){

        Log.d("darkiconmap: ",str_icon);
        if (str_icon.equals("clear-night")){
            iconview.setImageResource(R.drawable.weather_night); }
        else if (str_icon.equals("rain") ){
            Log.d("darkiconmap: ",str_icon);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
