package com.example.android.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.movieapp.R;
import com.example.android.movieapp.model.MoviesInfo;

import java.util.ArrayList;


public class TrailersAdapter extends BaseAdapter {
    private Context mContext;
    //ArrayList<Integer> image_id;
    ArrayList<MoviesInfo> movies_trailers=new ArrayList<>();

    public TrailersAdapter(Context c , ArrayList<MoviesInfo> movies_trailers) {
        this.mContext = c;
      //  this.image_id = image_id;
        this.movies_trailers=movies_trailers;
    }

    @Override
    public int getCount() {
        return movies_trailers.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return movies_trailers.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layout_inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layout_inflater.inflate(R.layout.trailer_list_view, null);
       // ImageView trailers = (ImageView) gridViewMovies.findViewById(R.id.trailers);
        TextView trailers_name = (TextView) view.findViewById(R.id.trailers_names);
      //  trailers.setImageResource(image_id.get(i));
        return view;
    }
}
