package com.example.android.movieapp.fragment;

import com.example.android.movieapp.database.FavoriteDB;
import com.example.android.movieapp.model.MoviesInfo;

import java.util.ArrayList;

public class FavoriteFragment extends BaseFragment {
    ArrayList<MoviesInfo> movies = new ArrayList<>();

    @Override
    String getMoviesUrl() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();

        final FavoriteDB favoriteDB = new FavoriteDB(getActivity());
        movies = favoriteDB.getAllContacts();
        onParsingFinish(movies);
    }
}
