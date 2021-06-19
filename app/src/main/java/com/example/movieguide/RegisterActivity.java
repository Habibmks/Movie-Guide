package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieguide.Firebase.Authentication;
import com.example.movieguide.Firebase.Firestore;
import com.example.movieguide.Functions.Functions;
import com.example.movieguide.collections.User.User;

public class RegisterActivity extends AppCompatActivity {

    static public int error;
    String str = "", toast="";
    Button registerbtn;
    EditText name,surname,username,email,password,repassword;
    String sname,ssurname,susername,semail,spassword,srepassword;
    Authentication auth;
    Firestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final TextView testview = findViewById(R.id.textView);
        registerbtn = findViewById(R.id.Registerbtn);
        Intent intent = getIntent();
        auth = new Authentication(this);
        if(intent.hasExtra("user_email")){
            str = intent.getStringExtra("user_email");
        }else {
            str = "intent bos";
            testview.setText(str);
        }
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        email = findViewById(R.id.editTextTextEmailAddress2);
        email.setText(str);
    }



    public void register(){
        name =  findViewById(R.id.editTextTextPersonName);
        surname = findViewById(R.id.editTextTextPersonName2);
        username = findViewById(R.id.editTextTextPersonName3);
        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);
        repassword = findViewById(R.id.editTextTextPassword3);
        sname = name.getText().toString(); ssurname = surname.getText().toString(); susername = username.getText().toString(); semail = email.getText().toString();
        spassword = password.getText().toString(); srepassword = repassword.getText().toString();
        if(!Functions.isConnected(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),"There is no connection",Toast.LENGTH_SHORT).show();
        }else{
            if (Functions.verifregis(sname, ssurname, susername, semail, spassword, srepassword, getApplicationContext())) {
                User user = new User(semail, spassword, sname, ssurname, susername);
                auth.Register(semail, spassword, user, new Authentication.register() {
                    @Override
                    public void onError(String message) {
                        if(message.equals("The email address is badly formatted.")){
                            Toast.makeText(getApplicationContext(),"Email is not valid",Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onResponse(User user) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("email",semail);
                        startActivity(intent);
                    }
                });
            } else {
                if (error == 0) name.requestFocus(); else if (error == 1) surname.requestFocus(); else if (error == 2) username.requestFocus(); else
                    if (error == 3) email.requestFocus(); else if (error == 4) password.requestFocus(); else if (error == 5) repassword.requestFocus();
            }
        }
    }
}