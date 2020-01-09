package com.example.weather_app.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.weather_app.R;

import java.util.ArrayList;

public class RecyclerView_adapter extends RecyclerView.Adapter<RecyclerView_adapter.ViewHolder> {

    private static final String TAG = "RecyclerView_adapter";

    private ArrayList<String> image_urls = new ArrayList<String>();
    private Context mContext;

    public RecyclerView_adapter(ArrayList<String> image_urls, Context mContext) {
        this.image_urls = image_urls;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflates the view, recycling the view, putting in position
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_list,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext).load(image_urls.get(i)).into(viewHolder.myimage);

    }

    @Override
    public int getItemCount() {
        return image_urls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView myimage;
        CardView parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myimage = itemView.findViewById(R.id.imageView);
            parent_layout = itemView.findViewById(R.id.list_view);

        }
    }
}
