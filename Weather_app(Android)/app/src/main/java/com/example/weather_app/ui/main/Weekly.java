package com.example.weather_app.ui.main;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather_app.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Weekly.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Weekly#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Weekly extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    protected LineData data;
    View view;
    JSONObject dark_api;

    // TODO: Rename and change types of parameters
//    private String mParam1;
    private String mParam2;
//    protected JSONObject dark_obj;

    private OnFragmentInteractionListener mListener;

    public Weekly() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment Weekly.
     */
    // TODO: Rename and change types and number of parameters
    public static Weekly newInstance(String dark) {
        Weekly fragment = new Weekly();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, dark);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        Log.d("dsffsd",mParam2);

        double[] temp_highs = new double[8];
        double[] temp_lows = new double[8];
        try {
            dark_api = new JSONObject(mParam2);
            JSONArray data_array = dark_api.getJSONObject("darkskyapi").getJSONObject("daily").getJSONArray("data");
            for (int i = 0; i < data_array.length(); i++) {
                temp_highs[i] = data_array.getJSONObject(i).getDouble("temperatureHigh");
                temp_lows[i] = data_array.getJSONObject(i).getDouble("temperatureLow");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EntryListcreate(temp_highs,temp_lows);
    }

    private void add_Vals() {
        TextView summary = view.findViewById(R.id.weeklySummary);
        ImageView iconview = view.findViewById(R.id.weeklyIcon);
        try {
            String dark_icon = dark_api.getJSONObject("darkskyapi").getJSONObject("daily").getString("icon");
            summary.setText(dark_api.getJSONObject("darkskyapi").getJSONObject("daily").getString("summary"));
            darkiconmap(iconview,dark_icon);
        } catch (JSONException e) {
            e.printStackTrace();
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
    public void EntryListcreate(double[] high, double[] low){
        List<Entry> entries1 = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < high.length; i++) {
            Float fmin = (float)low[i];// Minimum temperature from json
            Float fmax = (float)high[i]; // Maximum temperature from json
            entries1.add(new Entry(i, fmin));
            entries2.add(new Entry(i, fmax));
        }
        //Create a LineDataSet for each LineChart and change properties
        LineDataSet dataSet1 = new LineDataSet(entries1, "Minimum Temperature");
        dataSet1.setColor(Color.parseColor("#BB86FC"));
        LineDataSet dataSet2 = new LineDataSet(entries2, "Maximum Temperature");
        dataSet2.setColor(Color.parseColor("#FAAB1A"));
        //Render the chart
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        data = new LineData(dataSets);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weekly, container, false);
        add_Vals();
        LineChart chart = view.findViewById(R.id.chart);
        chart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        chart.getAxisRight().setTextColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getLegend().setTextColor(Color.WHITE);
        chart.getLegend().setTextSize(16.5f);
        chart.setData(data);
        chart.invalidate(); //refresh
        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
