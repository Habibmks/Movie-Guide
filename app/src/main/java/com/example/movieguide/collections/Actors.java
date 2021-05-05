package com.example.movieguide.collections;

import java.util.Comparator;

public class Actors {

    private int id;
    private String name;
    private String chname;
    private String poster;
    private int gender;
    private float popularity;

    public Actors(int id, String name, String chname, String poster, int gender,float popularity) {
        this.id = id;
        this.name = name;
        this.chname = chname;
        this.poster = "https://image.tmdb.org/t/p/original/" + poster;
        this.gender = gender;
        this.popularity = popularity;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getChname() {
        return chname;
    }

    public String getPoster() {
        return poster;
    }

    public int getGender() {
        return gender;
    }

    public float getPopularity(){
        return popularity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChname(String chname) {
        this.chname = chname;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public static Comparator<Actors> popularitycomparator = new Comparator<Actors>() {
        @Override
        public int compare(Actors a1, Actors a2) {
            //sort actors by their popularity
            Float.compare(a1.getPopularity(),a2.getPopularity());
            return 0;
        }
    };
}
