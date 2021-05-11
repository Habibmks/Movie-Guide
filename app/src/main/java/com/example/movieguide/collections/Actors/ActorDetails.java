package com.example.movieguide.collections.Actors;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ActorDetails extends Actors {

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String[] getKnown_names() {
        return known_names;
    }

    public void setKnown_names(String[] known_names) {
        this.known_names = known_names;
    }

    String biography,birthday;
    int size;
    String [] known_names;
    public ActorDetails(int id, String name, String poster, int gender, float popularity, String[] known_names, String biography,String birthday,int size) {
        super(id, name, poster, gender, popularity);
        this.known_names = known_names;
        this.biography = biography;
        this.birthday = birthday;
        this.size = size;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String agecalculator(String birthdate){
        LocalDate today = LocalDate.now();
        DateTimeFormatter  formatter = DateTimeFormatter.ISO_DATE;
        LocalDate localDate = LocalDate.parse(birthdate,formatter);
        int years = Period.between(localDate,today).getYears();
        return String.valueOf(years);
    }
}
