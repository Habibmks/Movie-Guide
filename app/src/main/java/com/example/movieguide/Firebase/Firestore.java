package com.example.movieguide.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.movieguide.collections.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Firestore {
    FirebaseFirestore firestore;
    String userid;
    public Firestore(String id) {
        firestore = FirebaseFirestore.getInstance();
        userid = id;
    }

    public void Register(User user){
        Log.d("user",user.getName());
        firestore.collection("Users").document(userid).set(user).addOnCompleteListener(task -> Log.d("Firestore Register", user.getName() + " is added"));
    }
    public interface login{
        void onError(String message);
        void onResponse(User user);
    }
    public void login(login listener){
        firestore.collection("Users").document(userid).get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            Log.d("Firebase get","User's name" + user.getName());
            listener.onResponse(user);
        }).addOnFailureListener(e -> listener.onError(e.getMessage()));
    }
    public interface itemcheck{
        void onResponse(Boolean bool);
    }
    public void checker(String type,String id, itemcheck listener){
        listgetter("Favorites", type, list -> listener.onResponse(list.contains(id) ? true:false));
    }
    public interface getlist{
        void onResponse(ArrayList<String> list);
    }
    public void listgetter(String collection,String type,getlist listener){
        firestore.collection(collection).document(userid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot snapshot = task.getResult();
                listener.onResponse(snapshot.exists() ? (ArrayList<String>) snapshot.get(type): new ArrayList<>());
            }
        });
    }
    public void addfav(String id,String type){

        listgetter("Favorites", type, list -> {
            if (!list.isEmpty()){
                if (list.contains(id)){
                    Log.d("List item","This item is already in list");
                }else {
                    list.add(id);
                    firestore.collection("Favorites").document(userid).update("movies",list);
                    Log.d("List item","Item is added");
                }
            }else {
                Map<String,Object> movies = new HashMap<>();
                movies.put("movies", Arrays.asList(id));
                firestore.collection("Favorites").document(userid).set(movies);
                Log.d("set","new movie added");
            }
            Log.d("List",list.toString());
        });
    }
}
