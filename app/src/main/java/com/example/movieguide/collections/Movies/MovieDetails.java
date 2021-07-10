package com.example.movieguide.collections.Movies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.movieguide.MovieSearchReturn;
import com.example.movieguide.R;
public class MovieDetails extends MovieSearchReturn{

    public MovieDetails(String original_title, String overview, String title, String releasedate, int id, String poster, String[] genres, int size) {
        super(original_title, overview, title, releasedate, id, poster, genres, size);
    }
}