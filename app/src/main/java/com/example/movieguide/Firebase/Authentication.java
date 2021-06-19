package com.example.movieguide.Firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.movieguide.collections.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Authentication {
    FirebaseAuth auth;
    Firestore db;
    Context context;
    public Authentication(Context context) {

        auth = FirebaseAuth.getInstance();
        this.context = context;
    }
    public interface register{
        void onError(String message);
        void onResponse(User user);
    }
    public void Register(String mail,String password,User user,register listener){
        auth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(authResult -> {
            db = new Firestore(FirebaseAuth.getInstance().getCurrentUser().getUid());
            db.Register(user);
            listener.onResponse(user);
            Log.d("success creation",user.getName());
        }).addOnFailureListener(e -> {
            listener.onError(e.getMessage());
        });
    }
    public interface login{
        void onError(String message);
        void onResponse(User user);
    }
    public void Login(String email,String password,login listener){

        auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    Log.d("Sign in","Signed in Successfully");
                    db = new Firestore(auth.getCurrentUser().getUid());
                    db.login(new Firestore.login() {
                        @Override
                        public void onError(String message) {
                            listener.onError("Firestore " + message);
                        }

                        @Override
                        public void onResponse(User user) {
                            listener.onResponse(user);
                        }
                    });
                })
                .addOnFailureListener(e -> listener.onError("Auth " + e.getMessage()));
    }
    public FirebaseAuth getAuth() {
        return auth;
    }
    public interface resetpass{
        void onResponse(Boolean bool);
    }
    public void resetpassword(String password,resetpass listener){
        auth.getCurrentUser().updatePassword(password).addOnCompleteListener(task -> {
            listener.onResponse(task.isSuccessful());
            Log.d("resetreturn",task.isSuccessful() ? "true":"false");
        });
    }
    public interface forgetpass{
        void onResponse(Boolean bool);
        void onError(String message);
    }
    public void forgetpassword(String mail,forgetpass listener){
        auth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> {
            listener.onResponse(true);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("forgetpassword",e.getMessage());
                listener.onError(e.getMessage());
            }
        });
    }
}
