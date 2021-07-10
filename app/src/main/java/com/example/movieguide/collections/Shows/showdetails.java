package com.example.movieguide.collections.Shows;

public class showdetails extends Shows {
    String status;

    public showdetails(int id, int size, String[] genres, String name, String overview, String poster, String original_name, String date,String status) {
        super(id, size, genres, name, overview, poster, original_name, date);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
