package com.example.android.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.android.movieapp.R;
import com.example.android.movieapp.model.MoviesInfo;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<MoviesInfo> movies_list;

    public CustomAdapter(Context c, ArrayList<MoviesInfo> movies_list) {
        this.mContext = c;
        this.movies_list = movies_list;
    }

    @Override
    public int getCount() {
        return movies_list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return movies_list.get(i).getId();
    }

    @Override
    public View getView(int i, View convert, ViewGroup viewGroup) {
        LayoutInflater layout_inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convert = layout_inflater.inflate(R.layout.grid_items, null);
        ImageView image_item = (ImageView) convert.findViewById(R.id.items_images);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185" + movies_list.get(i).getPoster_path()).noFade()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).fit().centerCrop()
                .into(image_item);
        return convert;
    }
}
