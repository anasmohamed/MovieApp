package com.example.android.movieapp.async;

import android.os.AsyncTask;
import com.example.android.movieapp.listener.OnParsingFinishListener;
import com.example.android.movieapp.model.MoviesInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MoviesAsyncTask extends AsyncTask<String, Void, ArrayList<MoviesInfo>> {
   private OnParsingFinishListener onParsingFinishListener;

    public MoviesAsyncTask(OnParsingFinishListener onParsingFinishListener){
        this.onParsingFinishListener = onParsingFinishListener;
    }

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

            ArrayList<MoviesInfo> movieList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MoviesInfo movies = new MoviesInfo();
                movies.setPoster_path(jsonObject.getString("poster_path"));
                movies.setTitle(jsonObject.getString("title"));
                movies.setOverview(jsonObject.getString("overview"));
                movies.setPopularity(jsonObject.getString("popularity"));
                movies.setVote_average(jsonObject.getString("vote_average"));
                movies.setRelease_date(jsonObject.getString("release_date"));
                movies.setId(jsonObject.getInt("id"));
                movieList.add(movies);
            }
            return movieList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
      return null;
    }

    @Override
    protected void onPostExecute(final ArrayList<MoviesInfo> movies_list) {
        super.onPostExecute(movies_list);
        onParsingFinishListener.onParsingFinish(movies_list);
    }
}