package com.example.android.movieapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.movieapp.model.MoviesInfo;

import java.util.ArrayList;


public class FavoriteDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movie_detail";
    public static final String MOVIE_TABLE_NAME = "movie";
    public static final String MOVIE_COLUMN_ID = "id";
    public static final String POSTER_COLUMN = "poster";
    public static final String TITLE_COLUMN = "title";
    public static final String PLOT_SYNOPSIS = "overview";
    public static final String USER_RATING_COLUMN = "rating";
    public static final String RELEASE_DATE_COLUMN = "release_date";
    public static final String REVIEWS_AUTHOR = "author";
    public static final String REVIEWS_CONTENT = "content";


    SQLiteDatabase db = getWritableDatabase();

    public FavoriteDB(Context context) {
        super(context, DATABASE_NAME, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + MOVIE_TABLE_NAME + "("
                + MOVIE_COLUMN_ID +
                " INTEGER PRIMARY KEY," + TITLE_COLUMN +
                " TEXT NOT NULL," + POSTER_COLUMN +
                " TEXT NOT NULL," + PLOT_SYNOPSIS +
                " TEXT NOT NULL," + USER_RATING_COLUMN +
                " TEXT NOT NULL," + RELEASE_DATE_COLUMN +
                " TEXT NOT NULL," + REVIEWS_AUTHOR +
                " TEXT NOT NULL," + REVIEWS_CONTENT +
                " TEXT NOT NULL"  +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        onCreate(db);
    }

    public void add_favorite(int id, String title, String poster, String plot_synpsis, String user_rating, String release_date,String reviews_author,String review_content) {
        ContentValues values = new ContentValues();
        values.put(MOVIE_COLUMN_ID, id);
        values.put(TITLE_COLUMN, title);
        values.put(POSTER_COLUMN, poster);
        values.put(PLOT_SYNOPSIS, plot_synpsis);
        values.put(USER_RATING_COLUMN, user_rating);
        values.put(RELEASE_DATE_COLUMN, release_date);
        values.put(REVIEWS_AUTHOR,reviews_author);
        values.put(REVIEWS_CONTENT,review_content);
        db.insert(MOVIE_TABLE_NAME, null, values);
    }


    public boolean isMovieInFavorite(String id){
        Cursor cursor = getReadableDatabase()
                .query(
                        MOVIE_TABLE_NAME,
                        null,
                        MOVIE_COLUMN_ID + "=? ",
                        new String[]{id},
                        null, null, null);

        if (cursor == null || cursor.getCount() <= 0){
            if (cursor != null) cursor.close();
            return false;
        }
        else return true;
    }
    public void remove_movie(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + MOVIE_TABLE_NAME + " WHERE " +MOVIE_COLUMN_ID  + "= '" + id + "'");
        database.close();
    }


    // Getting All Contacts
    public ArrayList<MoviesInfo> getAllContacts() {
        ArrayList<MoviesInfo> movies = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MOVIE_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through  all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MoviesInfo movie = new MoviesInfo();
                movie.setId((cursor.getInt(0)));
                movie.setTitle(cursor.getString(1));
                movie.setPoster_path(cursor.getString(2));
                movie.setOverview(cursor.getString(3));
                movie.setVote_average(cursor.getString(4));
                movie.setRelease_date(cursor.getString(5));
                movie.setReviews_author(cursor.getString(6));
                movie.setReviews_content(cursor.getString(7));
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        return movies;
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
