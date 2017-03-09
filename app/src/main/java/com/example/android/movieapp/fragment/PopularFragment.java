package com.example.android.movieapp.fragment;

;


public class PopularFragment extends BaseFragment {

    @Override
    String getMoviesUrl() {
        return "https://api.themoviedb.org/3/movie/popular?api_key=fafafa7b34f6bc864117117864178666&language=en-US";
    }


}

