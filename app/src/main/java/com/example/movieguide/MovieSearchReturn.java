package com.example.movieguide;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieSearchReturn {


    @Override
    public String toString() {
        return  "Original Title: " + original_title + '\'' +
                "\nOverview: " + overview + '\'' +
                "\nTitle: " + title + '\'' +
                "\nRelease Date" + releasedate + '\'' +
                "\nID: " + id
                ;
    }

    String original_title;
    String overview;
    String title;
    String releasedate;
    String poster;
    int id;
    public  MovieSearchReturn(){

    }
    public MovieSearchReturn(String original_title, String overview, String title, String releasedate, int id,String poster) {
        this.original_title = original_title;
        this.overview = overview;
        this.title = title;
        this.releasedate = releasedate;
        this.id = id;
        this.poster = "https://image.tmdb.org/t/p/original/" + poster;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

}
