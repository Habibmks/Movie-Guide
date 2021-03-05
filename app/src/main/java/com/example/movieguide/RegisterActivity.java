package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final TextView testview = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        if(intent.hasExtra("user_email")){
            str = intent.getStringExtra("user_email").toString();
            testview.setText(str);
        }else {
            str = "intent bos";
            testview.setText(str);
        }
    }
}