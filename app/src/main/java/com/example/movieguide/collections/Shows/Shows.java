package com.example.movieguide.collections.Shows;

public class Shows {

    int id,size;
    String[] genres;
    String name;
    String overview;
    String poster;
    String original_name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;
    public Shows(int id, int size, String[] genres, String name, String overview, String poster, String original_name,String date) {
        this.id = id;
        this.size = size;
        this.genres = genres;
        this.name = name;
        this.overview = overview;
        this.poster = "https://image.tmdb.org/t/p/original/" + poster;
        this.original_name = original_name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = "https://image.tmdb.org/t/p/original/" + overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }



}
