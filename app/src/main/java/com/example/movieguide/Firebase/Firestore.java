package com.example.movieguide.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.movieguide.collections.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Firestore {
    FirebaseFirestore firestore;
    String userid;
    static Boolean rtn;
    public Firestore(String id) {
        firestore = FirebaseFirestore.getInstance();
        userid = id;
    }

    public void Register(User user){
        Log.d("user",user.getName());
        firestore.collection("Users").document(userid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Firestore Register", user.getName() + " is added");
            }
        });
    }

}
