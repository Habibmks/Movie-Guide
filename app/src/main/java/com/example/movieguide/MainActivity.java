package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieguide.Firebase.Authentication;
import com.example.movieguide.Firebase.Firestore;
import com.example.movieguide.Functions.Functions;
import com.example.movieguide.collections.User.User;

import java.util.concurrent.atomic.AtomicReference;


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
        Authentication auth = new Authentication(getApplicationContext());
        auth.checkuser(new Authentication.chcuser() {
            @Override
            public void onResponse(String userid) {
                if (!userid.isEmpty()){
                    Firestore db = new Firestore(userid);
                    db.login(new Firestore.login() {
                        @Override
                        public void onError(String message) {
                            Log.d("Auto login",message);
                        }

                        @Override
                        public void onResponse(User user) {
                            Intent intent = new Intent(getApplicationContext(), Test.class);
                            intent.putExtra("user", user);
                            intent.putExtra("userid", auth.getAuth().getCurrentUser().getUid());
                            startActivity(intent);
                        }
                    });
                }else Log.d("First login","There is no signed in user");
            }

            @Override
            public void onError(String messge) {
                Log.d("Auto login","There is no signed in user");
            }
        });


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