package com.example.android.movieapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.movieapp.R;
import com.example.android.movieapp.fragment.DetailFragment;
import com.example.android.movieapp.model.MoviesInfo;

public class DetailActivity extends AppCompatActivity {

    private MoviesInfo moviesInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (savedInstanceState == null){
        intentData();
        startFragment();
    }}

    private void intentData() {
        moviesInfo = getIntent().getParcelableExtra("MoviesInfo");
    }

    private void startFragment() {
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setMoviesInfo(moviesInfo);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, detailFragment)
                .commit();
    }
}




