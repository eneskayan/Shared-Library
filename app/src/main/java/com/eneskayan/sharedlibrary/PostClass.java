package com.eneskayan.sharedlibrary;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PostClass extends RecyclerView.Adapter<PostClass.ViewHolder> {


    private static final String TAG = "PostClass";

    private ArrayList<String> mBaslik = new ArrayList<>();
    private ArrayList<String> mFotoUrl = new ArrayList<>();

    public PostClass( Context mContext, ArrayList<String> mBaslik, ArrayList<String> mFotoUrl) {
        this.mBaslik = mBaslik;
        this.mFotoUrl = mFotoUrl;
        this.mContext = mContext;
    }

    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG,"onCreateViewHolder: called.");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_view,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG,"onBindViewHolder: called.");

        //ArrayList<String> mFotoUrlGroupBy = mFotoUrl.stream().collect(Collectors.groupingBy(w -> w.baslik));
        Glide.with(mContext).asBitmap()
                .load(mFotoUrl
                .get(i))
                .into(viewHolder.image);

        viewHolder.baslik.setText(mBaslik.get(i));

        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: clicked on an image" + mBaslik.get(i));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mBaslik.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView baslik;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewCustomview);
            baslik = itemView.findViewById(R.id.baslikCustomview);
        }
    }
}