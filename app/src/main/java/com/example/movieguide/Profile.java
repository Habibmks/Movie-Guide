package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieguide.Firebase.Authentication;
import com.example.movieguide.collections.User.User;

public class Profile extends AppCompatActivity {
    User user;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView tvname,tvsurname,tvnick,tvmail;
        EditText etpassword = findViewById(R.id.etprofilepassword);
        Button password = findViewById(R.id.btnprofilepassword);
        tvname = findViewById(R.id.tvusername); tvsurname = findViewById(R.id.tvusersurname); tvnick = findViewById(R.id.tvusernick); tvmail = findViewById(R.id.tvusermail);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        userid = intent.getStringExtra("userid");
        Authentication authentication = new Authentication(this);
        tvname.setText("Name: " + user.getName()); tvsurname.setText("Surname: " + user.getSurname()); tvnick.setText("Username: " + user.getUsername()); tvmail.setText(user.getMail());
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authentication.resetpassword(etpassword.getText().toString(), new Authentication.resetpass() {
                    @Override
                    public void onResponse(Boolean bool) {
                        Toast.makeText(Profile.this,bool ? "Succesfull":"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}