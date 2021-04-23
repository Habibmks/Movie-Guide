package com.example.movieguide;

import java.util.Comparator;

public class Actors {

    private final int id;
    private final String name;
    private final String chname;
    private final String poster;
    private final int gender;
    private final float popularity;

    public Actors(int id, String name, String chname, String poster, int gender,float popularity) {
        this.id = id;
        this.name = name;
        this.chname = chname;
        this.poster = poster;
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

    public static Comparator<Actors> popularitycomparator = new Comparator<Actors>() {
        @Override
        public int compare(Actors a1, Actors a2) {
            //sort actors by their popularity
            Float.compare(a1.getPopularity(),a2.getPopularity());
            return 0;
        }
    };
}
