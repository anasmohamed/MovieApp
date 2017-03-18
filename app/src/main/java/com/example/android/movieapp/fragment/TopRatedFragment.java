package com.example.android.movieapp.fragment;

public class TopRatedFragment extends BaseFragment {

    @Override
    String getMoviesUrl() {
        return "https://api.themoviedb.org/3/movie/top_rated?api_key=//Your API Key&language=en-US";
    }
}
