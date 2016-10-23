package com.anas.movieapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MovieTask extends AsyncTask<String, Void, ArrayList<MoviesInfo>> {
    private Context context;
    ArrayList<MoviesInfo> movieList = new ArrayList<MoviesInfo>();
    GridView view;

    public MovieTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<MoviesInfo> doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            StringBuilder sb = null;
            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                reader.close();
            }
            JSONObject bigJsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = bigJsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MoviesInfo movies = new MoviesInfo();
                movies.setPoster_path(jsonObject.getString("poster_path"));
                movies.setTitle(jsonObject.getString("title"));
                movies.setOverview(jsonObject.getString("overview"));
                movies.setPopularity(jsonObject.getString("popularity"));
                movieList.add(movies);

            }

            return movieList;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    @Override
    protected void onPostExecute(ArrayList<MoviesInfo> movies_list) {
        super.onPostExecute(movies_list);
        MainActivity activity =new MainActivity();
        Toast.makeText(context,movies_list +"",Toast.LENGTH_SHORT).show();
       CustomAdapter customAdapter = new CustomAdapter(context, movies_list);
        activity.mGridView.setAdapter(customAdapter);
//customAdapter.notifyDataSetChanged();
    }

}
