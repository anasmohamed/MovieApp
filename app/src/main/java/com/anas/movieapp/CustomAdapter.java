package com.anas.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<MoviesInfo> movie_list= new ArrayList<>();

    public CustomAdapter(Context c, ArrayList<MoviesInfo> movie_list) {
        this.mContext = c;
        this.movie_list = movie_list;
    }

    @Override
    public int getCount() {
        return movie_list.size();
    }

    @Override
    public Object getItem(int i) {
        return movie_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflat = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflat.inflate(R.layout.grid_items, null);
        ImageView image_item = (ImageView)convertView.findViewById(R.id.items_images);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + movie_list.get(i)).noFade().fit().centerCrop().into(image_item);
       return convertView;
    }
}



