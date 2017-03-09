package com.example.android.movieapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.movieapp.R;
import com.example.android.movieapp.activity.DetailActivity;
import com.example.android.movieapp.activity.MainActivity;
import com.example.android.movieapp.adapter.CustomAdapter;
import com.example.android.movieapp.async.MoviesAsyncTask;
import com.example.android.movieapp.listener.OnParsingFinishListener;
import com.example.android.movieapp.model.MoviesInfo;

import java.util.ArrayList;



public abstract class BaseFragment extends Fragment implements
        OnParsingFinishListener,
        AdapterView.OnItemClickListener {
    protected GridView gridViewMovies;
    protected ArrayList<MoviesInfo> moviesInfoList;

    abstract String getMoviesUrl();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base, container, false);

        gridViewMovies = (GridView) getActivity().findViewById(R.id.gridViewMovies);
        gridViewMovies.setOnItemClickListener(this);


        if (isNetworkAvailable()) {
            if (getMoviesUrl() != null)
                new MoviesAsyncTask(this).execute(getMoviesUrl());
        } else {
            Toast.makeText(getActivity(), "Check The Internet Connection", Toast.LENGTH_LONG).show();
        }
        return v;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onParsingFinish(final ArrayList<MoviesInfo> moviesInfoList) {
        if (moviesInfoList == null) return;
        this.moviesInfoList = moviesInfoList;

        CustomAdapter adapter = new CustomAdapter(getActivity(), moviesInfoList);
        gridViewMovies.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (((MainActivity) getActivity()).isTwoPane()) {
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setMoviesInfo(moviesInfoList.get(i));

            FragmentManager fragmentMr = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentMr.beginTransaction();
            ft.replace(R.id.movie_detail_container, detailFragment);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("name", intent);
            Bundle bundle = null;
            intent.putExtras(bundle);
            intent.putExtra("MoviesInfo", moviesInfoList.get(i));
            startActivity(intent);
        }


    }
}

