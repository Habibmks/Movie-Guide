package com.example.movieguide.Firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.movieguide.collections.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Authentication {
    FirebaseAuth auth;
    Firestore db;
    Context context;
    static Boolean rtn=false;
    public Authentication(Context context) {

        auth = FirebaseAuth.getInstance();
        this.context = context;
    }
    public void Register(String mail,String password,User user){
        auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                db = new Firestore(FirebaseAuth.getInstance().getCurrentUser().getUid());
                db.Register(user);
            }
        });
    }
    public FirebaseAuth getAuth() {
        return auth;
    }
}
