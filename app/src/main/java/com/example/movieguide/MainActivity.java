package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button login = (Button) findViewById(R.id.loginbtn);
        final Button toregbtn = (Button) findViewById(R.id.logtoregbtn);
        final EditText logemail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        final EditText logpass = (EditText) findViewById(R.id.editTextTextPassword);
        final TextView tverror = (TextView) findViewById(R.id.textView2);
        logemail.setText("admin");
        logpass.setText("admin");
        toregbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logemail.getText().toString().isEmpty()){
                    Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                    intent.putExtra("user_email","");
                    startActivity(intent);
                    /*Toast t = Toast.makeText(getApplicationContext(),"Email bos",Toast.LENGTH_SHORT);
                    t.show();*/
                }else{
                    Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                    intent.putExtra("user_email",logemail.getText().toString());
                    startActivity(intent);
                    /*Toast t = Toast.makeText(getApplicationContext(),logemail.getText().toString(),Toast.LENGTH_SHORT);
                    t.show();*/
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(tverror);
            }
        });
    }
    public void register(){
        final EditText logemail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        final TextView testtv = (TextView) findViewById(R.id.WellcomeTv) ;

    }
    public void login(TextView tv){
        final EditText logemail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        final EditText logpassw = (EditText) findViewById(R.id.editTextTextPassword);

        String email = logemail.getText().toString(), password = logpassw.getText().toString();

        if (email.equals("admin") && password.equals("admin")){
            Intent intent = new Intent(this,Test.class);
            intent.putExtra("email",email);
            startActivity(intent);
        }else tv.setText("Username or Password is wrong");
    }
}