package com.anas.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=fafafa7b34f6bc864117117864178666&language=en-US\n";
    GridView mGridView;
   ArrayList<MoviesInfo> URL = new ArrayList<MoviesInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toast.makeText(MainActivity.this,URL+"",Toast.LENGTH_SHORT).show();
//        CustomAdapter customAdapter =new CustomAdapter(MainActivity.this,URL);


//        mGridView.setAdapter(customAdapter);
        new MovieTask(this).execute(BASE_URL);
        mGridView =(GridView)findViewById(R.id.father_grid);
}
}
