package com.example.movieguide.collections.User;

import java.io.Serializable;

public class Favorites implements Serializable {
    int moviesize,seriessize,actorsize;

    public String[] movies = new String[moviesize];
    public String[] series = new String[seriessize];
    public String [] actors = new String[actorsize];

    public Favorites(){};
    public Favorites(int moviesize, int seriessize, int actorsize, String[] movies, String[] series, String[] actors) {
        this.moviesize = moviesize;
        this.seriessize = seriessize;
        this.actorsize = actorsize;
        this.movies = movies;
        this.series = series;
        this.actors = actors;
    }

    public int getMoviesize() {
        return moviesize;
    }

    public void setMoviesize(int moviesize) {
        this.moviesize = moviesize;
    }

    public int getSeriessize() {
        return seriessize;
    }

    public void setSeriessize(int seriessize) {
        this.seriessize = seriessize;
    }

    public int getActorsize() {
        return actorsize;
    }

    public void setActorsize(int actorsize) {
        this.actorsize = actorsize;
    }

    public String[] getMovies() {
        return movies;
    }

    public void setMovies(String[] movies) {
        this.movies = movies;
    }

    public String[] getSeries() {
        return series;
    }

    public void setSeries(String[] series) {
        this.series = series;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }
}
