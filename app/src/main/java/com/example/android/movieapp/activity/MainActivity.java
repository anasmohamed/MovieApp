package com.example.android.movieapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.movieapp.R;
import com.example.android.movieapp.fragment.FavoriteFragment;
import com.example.android.movieapp.fragment.PopularFragment;
import com.example.android.movieapp.fragment.TopRatedFragment;


public class MainActivity extends AppCompatActivity {
    private View movieDetailsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieDetailsContainer = findViewById(R.id.movie_detail_container);
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();

        PopularFragment popularFragment = new PopularFragment();
        TopRatedFragment topRatedFragment = new TopRatedFragment();
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        String preferences = PreferenceManager.getDefaultSharedPreferences(this).getString("Selected_frag","Pop");
        FragmentManager fragmentMr = getSupportFragmentManager();
        FragmentTransaction ft = fragmentMr.beginTransaction();
        if (preferences.equalsIgnoreCase("Top")) {
            ft.replace(R.id.fragment_replace, topRatedFragment);
        } else if (preferences.equalsIgnoreCase("Fav")) {
            ft.replace(R.id.fragment_replace, favoriteFragment);
        } else {
            ft.replace(R.id.fragment_replace, popularFragment);
        }

        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment selectedFragment = null;
        SharedPreferences.Editor editor =PreferenceManager.getDefaultSharedPreferences(this).edit();
        switch (item.getItemId()) {
            case R.id.popular:
                selectedFragment = new PopularFragment();
                editor.putString("Selected_frag", "Pop");
                break;

            case R.id.top_rated:
                selectedFragment = new TopRatedFragment();
                editor.putString("Selected_frag", "Top");

                break;

            case R.id.favorite:
                selectedFragment = new FavoriteFragment();
                editor.putString("Selected_frag", "Fav");
                break;
        }
        editor.commit();
        editor.clear();
        if (selectedFragment == null) return false;
        FragmentManager fragmentMr = getSupportFragmentManager();
        FragmentTransaction ft = fragmentMr.beginTransaction();
        ft.replace(R.id.fragment_replace, selectedFragment);
        ft.addToBackStack(null);
        ft.commit();

        return true;
    }

    public boolean isTwoPane() {
        Log.e("isTwoPane", "movieDetailsContainer != null = " + (movieDetailsContainer != null));
        return movieDetailsContainer != null;
    }

}
