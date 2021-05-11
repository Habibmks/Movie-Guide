package com.example.movieguide.collections.Actors;

public class ActorLister extends Actors {
    String character_name;
    public ActorLister(int id, String name, String poster, int gender, float popularity, String character_name) {
        super(id, name, poster, gender, popularity);
        this.character_name = character_name;
    }
}
