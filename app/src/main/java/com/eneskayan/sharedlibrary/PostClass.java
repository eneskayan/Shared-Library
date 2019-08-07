package com.eneskayan.sharedlibrary;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostClass extends ArrayAdapter<String> {

    private final ArrayList<String> userbaslik;
    private final ArrayList<String> userImage;
    private final ArrayList<String> userEmail;
    private  final Activity context;


    public PostClass(ArrayList<String> userbaslik, ArrayList<String> userImage, ArrayList<String> userEmail, Activity context) {
        super(context,R.layout.custom_view,userEmail);
        this.userbaslik = userbaslik;
        this.userImage = userImage;
        this.userEmail = userEmail;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view,null,true);

        TextView userEmailText = customView.findViewById(R.id.useremailCustomview);
        TextView basliktext = customView.findViewById(R.id.baslikCustomview);
        ImageView imageView = customView.findViewById(R.id.imageViewCustomview);

        userEmailText.setText(userEmail.get(position));
        basliktext.setText(userbaslik.get(position));

        Picasso.get().load(userImage.get(position)).into(imageView);



        return customView;
    }
}
