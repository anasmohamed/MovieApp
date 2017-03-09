package com.example.android.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;
public class MoviesInfo implements Parcelable{
    private String poster_path;
    private String overview;
    private String release_date;
    private String title;
    private String popularity;
    private String vote_average;
    private int id;
    private String trailer;
    private String reviews_author;
    private String reviews_content;

    public MoviesInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }


    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getReviews_author() {
        return reviews_author;
    }

    public void setReviews_author(String reviews_author) {
        this.reviews_author = reviews_author;
    }

    public String getReviews_content() {
        return reviews_content;
    }

    public void setReviews_content(String reviews_content) {
        this.reviews_content = reviews_content;
    }

    private MoviesInfo(Parcel in) {

        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        title = in.readString();
        popularity = in.readString();
        vote_average = in.readString();
        id = in.readInt();
        trailer = in.readString();
        reviews_author = in.readString();
        reviews_content = in.readString();

    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(poster_path);
        out.writeString(overview);
        out.writeString(release_date);
        out.writeString(title);
        out.writeString(popularity);
        out.writeString(vote_average);
        out.writeInt(id);
        out.writeString(trailer);
        out.writeString(reviews_author);
        out.writeString(reviews_content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MoviesInfo> CREATOR = new Parcelable.Creator<MoviesInfo>() {
        public MoviesInfo createFromParcel(Parcel in) {
            return new MoviesInfo(in);
        }

        public MoviesInfo[] newArray(int size) {
            return new MoviesInfo[size];
        }
    };
}
