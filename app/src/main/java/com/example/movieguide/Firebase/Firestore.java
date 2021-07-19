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

    public void Register(User user) {
        Log.d("user", user.getName());
        firestore.collection("Users").document(userid).set(user)
                .addOnCompleteListener(task -> Log.d("Firestore Register", user.getName() + " is added"));
    }

    public interface login {
        void onError(String message);

        void onResponse(User user);
    }

    public void login(login listener) {
        firestore.collection("Users").document(userid).get().
                addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    Log.d("Firebase get", "User's name" + user.getName());
                    listener.onResponse(user);
                })
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    public interface itemcheck {
        void onResponse(Boolean bool);
    }

    public void checker(String type, String id, itemcheck listener) {
        listgetter("Favorites", type, new getlist() {
            @Override
            public void onResponse(ArrayList<String> list) {
                if (list != null) {
                    listener.onResponse(list.contains(id));
                }else listener.onResponse(false);
            }

            @Override
            public void onError(String message) {
                Log.d("checker",message);
                listener.onResponse(false);
            }
        });
    }

    public interface getlist {
        void onResponse(ArrayList<String> list);

        void onError(String message);
    }

    public void listgetter(String collection, String type, getlist listener) {
        try {
            firestore.collection(collection).document(userid).get().addOnSuccessListener(DocumentSnapshot -> {
                listener.onResponse((ArrayList<String>) DocumentSnapshot.get(type));
            }).addOnFailureListener(e -> listener.onError(e.getMessage()));
        } catch (Exception e) {
            Log.d("listgetter",e.getMessage());
            ArrayList<String> list = new ArrayList<>();
//            listener.onResponse(list);
            listener.onError(e.getMessage());

        }
//        try {
//            firestore.collection(collection).document(userid).get().addOnCompleteListener(task -> {
//
//                if (task.isSuccessful()) {
//
//                    DocumentSnapshot snapshot = task.getResult();
//
//                    listener.onResponse(snapshot.exists() ? (ArrayList<String>) snapshot.get(type) : new ArrayList<>());
//
//                }
//
//            });
//
//        }catch (Exception e){
//            listener.onError(e.getMessage());
//        }
    }

    public void addfav(String id, String type) {
        listgetter("Favorites", type, new getlist() {
            @Override
            public void onResponse(ArrayList<String> list) {
                if (list!=null) {
                    if (list.contains(id)) {
                        Log.d("List item", "This item is already in list");
                    } else {
                        list.add(id);
                        firestore.collection("Favorites").document(userid).update(type, list);
                        Log.d("List item", "Item is added");
                    }
                } else {
                    Map<String, Object> object = new HashMap<>();
                    Log.d("addfav4",object.toString());
                    object.put(type, Arrays.asList(id));
                    Log.d("addfav5",id);
                    Log.d("addfav6",object.toString());
                    firestore.collection("Favorites").document(userid).update(object);
                    Log.d("set", "new "+type+" added");
                }
            }

            @Override
            public void onError(String message) {
                Log.d("addfav", message);
                Map<String, Object> object = new HashMap<>();
                Log.d("addfav2",id);
                object.put(type, Arrays.asList(id));
                Log.d("addfav3",object.toString());
                Log.d("addfav7",String.valueOf(userid.isEmpty()));
                firestore.collection("Favorites").document(userid).update(object);
                Log.d("set", "new "+type+" added");
            }
        });


    }

    public void deletefav(String id, String type) {

        listgetter("Favorites", type, new getlist() {
            @Override
            public void onResponse(ArrayList<String> list) {
                if (list.isEmpty()) {
                    Log.d("deletefav", "List is Empty");
                } else {
                    list.remove(id);
                    firestore.collection("Favorites").document(userid).update(type, list)
                            .addOnSuccessListener(documentSnapshot -> {
                                Log.d("deletefav", id + " Deleted Succesfully");
                            })
                            .addOnFailureListener(e -> {
                                Log.d("deletefav", e.getMessage());
                            });
                }
            }

            @Override
            public void onError(String message) {
                Log.d("deletefav", message);
            }
        });
    }
}
