package com.example.movieguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieguide.Firebase.Authentication;
import com.example.movieguide.Functions.Functions;
import com.example.movieguide.collections.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    static public int error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button login = (Button) findViewById(R.id.loginbtn);
        final Button toregbtn = (Button) findViewById(R.id.logtoregbtn);
        final EditText logemail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        final EditText logpass = (EditText) findViewById(R.id.editTextTextPassword);
        Button btnpass = findViewById(R.id.btnforgetpass);

        login.setOnClickListener(v -> {

            if(!Functions.isConnected(this)){
                Toast.makeText(MainActivity.this,"There is no connection",Toast.LENGTH_SHORT).show();
            }else {
                if(Functions.veriflogin(logemail.getText().toString(),logpass.getText().toString(),getApplicationContext())) {
                    Functions.login(logemail.getText().toString(), logpass.getText().toString(), MainActivity.this);
                }else{
                    if (error==0){
                        logemail.requestFocus();
                    }else if(error==1) {
                        logpass.requestFocus();
                    }
                }
            }
        });

        toregbtn.setOnClickListener(v -> {
            if (logemail.getText().toString().isEmpty()){
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                intent.putExtra("user_email","");
                startActivity(intent);

            }else{
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                intent.putExtra("user_email",logemail.getText().toString());
                startActivity(intent);

            }
        });
        btnpass.setOnClickListener(v -> {
            if (logemail.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter mail", Toast.LENGTH_SHORT).show();
            } else {
                Functions.reset(logemail.getText().toString(),MainActivity.this);
            }
        });
    }

}