package com.example.android.movieapp.fragment;

;


public class PopularFragment extends BaseFragment {

    @Override
    String getMoviesUrl() {
        return "https://api.themoviedb.org/3/movie/popular?api_key=Your Api Key&language=en-US";
    }


}

