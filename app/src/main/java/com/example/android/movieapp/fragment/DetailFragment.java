package com.example.android.movieapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movieapp.R;
import com.example.android.movieapp.activity.MainActivity;
import com.example.android.movieapp.adapter.TrailersAdapter;
import com.example.android.movieapp.database.FavoriteDB;
import com.example.android.movieapp.helper.Helper;
import com.example.android.movieapp.model.MoviesInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class DetailFragment extends Fragment {

    TextView title;
    ImageView poster;
    TextView overview;
    TextView vote;
    TextView release_date;
    TextView review_author;
    TextView review_content;
    TextView review_author1;
    TextView review_content1;
    FloatingActionButton fab;
    String income_title;
    String income_poster;
    String income_overview;
    String income_vote;
    int income_id;
    String income_release_date;
    String income_reviews_url;
    ArrayList<String> authors = new ArrayList<>();
    ArrayList<String> contents = new ArrayList<>();
    ListView trailers;
    FavoriteDB favoriteDB;
    String income_trailer_url;
    private MoviesInfo moviesInfo;
    BaseFragment baseFragment;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detai, container, false);
        //this data is coming from fragments
        initViews(v);
        setData();
        income_id = moviesInfo.getId();
        income_trailer_url = "https://api.themoviedb.org/3/movie/" + income_id + "/videos?api_key=fafafa7b34f6bc864117117864178666&language=en-US";
        income_reviews_url = "https://api.themoviedb.org/3/movie/" + income_id + "/reviews?api_key=fafafa7b34f6bc864117117864178666&language=en-US";
        new trailersAsyncTask().execute(income_trailer_url);
        new reviewsAsyncTask().execute(income_reviews_url);


        return v;
    }

    private void initViews(View v) {
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        title = (TextView) v.findViewById(R.id.titel_textview);
        poster = (ImageView) v.findViewById(R.id.poster_image);
        favoriteDB = new FavoriteDB(getContext());
        review_author = (TextView) v.findViewById(R.id.reviews_author);
        review_content = (TextView) v.findViewById(R.id.reviews_content);
        review_author1 = (TextView) v.findViewById(R.id.reviews_author);
        review_content1 = (TextView) v.findViewById(R.id.reviews_content);
        overview = (TextView) v.findViewById(R.id.overview_tv);
        release_date = (TextView) v.findViewById(R.id.releas_date_tv);
        vote = (TextView) v.findViewById(R.id.vote_tv);
        baseFragment = new FavoriteFragment();
    }

    public void setMoviesInfo(MoviesInfo moviesInfo) {
        this.moviesInfo = moviesInfo;
    }


    private void setData() {
        income_id = moviesInfo.getId();
        income_title = moviesInfo.getTitle();
        income_overview = moviesInfo.getOverview();
        income_vote = moviesInfo.getVote_average();
        income_release_date = moviesInfo.getRelease_date();
        income_poster = moviesInfo.getPoster_path();
        //the download of images and put it into gridView
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185" + income_poster).noFade()
                .into(poster);
        // set the income data into views
        title.setText(moviesInfo.getTitle());
        overview.setText(moviesInfo.getOverview());
        vote.setText(moviesInfo.getVote_average());
        release_date.setText(moviesInfo.getRelease_date());
        if (favoriteDB.isMovieInFavorite(String.valueOf(income_id)) && (baseFragment.getMoviesUrl() == null)) {
            fab.setImageResource(R.drawable.ic_favorite);
        }

    }

    public class trailersAsyncTask extends AsyncTask<String, Void, ArrayList<MoviesInfo>> {
        ArrayList<MoviesInfo> movieList = new ArrayList<>();

        @Override
        protected ArrayList<MoviesInfo> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                StringBuilder sb = new StringBuilder();
                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    reader.close();
                }
                JSONObject bigJsonObject = new JSONObject(sb.toString());
                JSONArray jsonArray = bigJsonObject.getJSONArray("results");
                for (int i = 0; i < (jsonArray.length()); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    MoviesInfo movies = new MoviesInfo();
                    movies.setTrailer(jsonObject.getString("key"));
                    movieList.add(movies);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return movieList;
        }

        @Override
        protected void onPostExecute(final ArrayList<MoviesInfo> movies_list) {
            super.onPostExecute(movies_list);
            TrailersAdapter trailersAdapter = new TrailersAdapter(getActivity(), movies_list);
            trailers = (ListView) getActivity().findViewById(R.id.trailer_url);
            trailersAdapter.notifyDataSetChanged();
            trailers.setAdapter(trailersAdapter);
            Helper.getListViewSize(trailers);
            trailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + movies_list.get(i).getTrailer()));
                    startActivity(intent);
                }
            });

        }
    }

    public class reviewsAsyncTask extends AsyncTask<String, Void, ArrayList<MoviesInfo>> {
        ArrayList<MoviesInfo> movieList1 = new ArrayList<>();

        @Override
        protected ArrayList<MoviesInfo> doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                conn1.setRequestMethod("GET");
                StringBuilder sb1 = new StringBuilder();
                if (conn1.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb1.append(line + "\n");
                    }
                    reader.close();
                }
                JSONObject bigJsonObject1 = new JSONObject(sb1.toString());
                JSONArray jsonArray1 = bigJsonObject1.getJSONArray("results");
                for (int i = 0; i < (jsonArray1.length()); i++) {
                    JSONObject jsonObject = jsonArray1.getJSONObject(i);
                    MoviesInfo movies = new MoviesInfo();
                    movies.setReviews_content(jsonObject.getString("content"));
                    movies.setReviews_author(jsonObject.getString("author"));
                    movieList1.add(movies);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return movieList1;
        }

        @Override
        protected void onPostExecute(final ArrayList<MoviesInfo> movies_list) {
            super.onPostExecute(movies_list);
            for (int i = 0; i < movies_list.size(); i++) {
                authors.add(movies_list.get(i).getReviews_author());
                contents.add(movies_list.get(i).getReviews_content());
            }
            if (!authors.isEmpty()) {
                review_author1.setText(authors.get(0));
                review_content1.setText(contents.get(0));
            }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (favoriteDB.isMovieInFavorite(String.valueOf(income_id)) && (baseFragment.getMoviesUrl() == null)) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("unFavorite")
                                .setMessage("Are you sure you want to delete this movie?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        favoriteDB.remove_movie(String.valueOf(income_id));
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getActivity(), "You Are not unFavorite this Movie", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        if (!authors.isEmpty()) {
                            favoriteDB.add_favorite(income_id, income_title, income_poster, income_overview, income_vote, income_release_date, authors.get(0), contents.get(0));
                            fab.setImageResource(R.drawable.ic_favorite);
                        } else {
                            favoriteDB.add_favorite(income_id, income_title, income_poster, income_overview, income_vote, income_release_date, "", "");
                            fab.setImageResource(R.drawable.ic_favorite);
                        }

                    }
                }

            });
        }
    }
}


