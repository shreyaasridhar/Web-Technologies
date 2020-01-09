package com.example.weather_app.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Photos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Photos#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Photos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
    private String mParam2;
    protected ArrayList<String> image_links = new ArrayList<>();

    public Photos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment Photos.
     */
    // TODO: Rename and change types and number of parameters
    public static Photos newInstance(String image_data) {
        Photos fragment = new Photos();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, image_data);
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
        Log.d("Image_params",mParam2);
//        image_links = new String[8];
        try {
            JSONObject dark_api = new JSONObject(mParam2);
            JSONArray data_array = dark_api.getJSONArray("items");
            for (int i = 0; i < data_array.length(); i++) {
                image_links.add(data_array.getJSONObject(i).getString("link"));
//                Log.d("images",image_links.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView_adapter adapter = new RecyclerView_adapter(image_links,this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;

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
