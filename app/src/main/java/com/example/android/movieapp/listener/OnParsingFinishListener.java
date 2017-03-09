package com.example.android.movieapp.listener;

import com.example.android.movieapp.model.MoviesInfo;

import java.util.ArrayList;


public interface OnParsingFinishListener {
     void onParsingFinish(ArrayList<MoviesInfo> moviesInfoList);
}
