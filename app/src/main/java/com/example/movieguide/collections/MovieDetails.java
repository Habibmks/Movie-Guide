package com.example.movieguide.collections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.movieguide.R;

public class MovieDetails {

    private final String originalname;
    private final String overview;
    private final String posterpath;
    private final String releasedate;

    public MovieDetails(String originalname, String overview, String posterpath, String releasedate) {
        this.originalname = originalname;
        this.overview = overview;
        this.posterpath = posterpath;
        this.releasedate = releasedate;
    }

    public String getOriginalname() {
        return originalname;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public String getReleasedate() {
        return releasedate;
    }

}