package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    String str = "", toast="";
    Button registerbtn;
    EditText name,surname,username,email,password,repassword;
    String sname,ssurname,susername,semail,spassword,srepassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final TextView testview = findViewById(R.id.textView);
        registerbtn = findViewById(R.id.Registerbtn);
        Intent intent = getIntent();
        if(intent.hasExtra("user_email")){
            str = intent.getStringExtra("user_email").toString();
            testview.setText(str);
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
    }
    public void register(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        name =  findViewById(R.id.editTextTextPersonName);
        surname = findViewById(R.id.editTextTextPersonName2);
        username = findViewById(R.id.editTextTextPersonName3);
        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);
        repassword = findViewById(R.id.editTextTextPassword3);
        sname = name.getText().toString(); ssurname = surname.getText().toString(); susername = username.getText().toString(); semail = email.getText().toString();
        spassword = password.getText().toString(); srepassword = repassword.getText().toString();
        boolean rtn = false;

        if (sname.isEmpty() || ssurname.isEmpty() || susername.isEmpty() || semail.isEmpty() || spassword.isEmpty() || srepassword.isEmpty()){
            toast = "There are empty fields";
            Toast t = Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_SHORT);
            t.show();
        }else if(!spassword.equals(srepassword)){
            toast = "Passwords are not matched";
            Toast t = Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_SHORT);
            t.show();
        }else rtn = true;
        if(rtn){
            toast = "Everything is True";
            Toast t = Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_SHORT);
            t.show();
        }

    }
}