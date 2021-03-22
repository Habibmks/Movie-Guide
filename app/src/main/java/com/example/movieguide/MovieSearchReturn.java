package com.example.movieguide;

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
    int id;
    public  MovieSearchReturn(){

    }
    public MovieSearchReturn(String original_title, String overview, String title, String releasedate, int id) {
        this.original_title = original_title;
        this.overview = overview;
        this.title = title;
        this.releasedate = releasedate;
        this.id = id;
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
}
