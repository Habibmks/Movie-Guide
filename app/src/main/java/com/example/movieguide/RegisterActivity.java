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
import com.example.movieguide.collections.User.User;

public class RegisterActivity extends AppCompatActivity {
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
            str = intent.getStringExtra("user_email").toString();
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
            User user = new User(semail,spassword,sname,ssurname,susername);
            auth.Register(semail,spassword,user);
//            Toast t = Toast.makeText(getApplicationContext(),"Everything is true",Toast.LENGTH_SHORT);
//            t.show();
//            mAuth = FirebaseAuth.getInstance();
//            mAuth.createUserWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        User user = new User(semail,spassword,sname,ssurname,susername);
//                        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()){
//                                    Toast.makeText(RegisterActivity.this,susername+"is added",Toast.LENGTH_SHORT).show();
//                                }else {
//                                    Log.d(getAttributionTag(), "firestone hatasi");
//                                }
//                            }
//                        });
//                    }else {
//                        Log.d(getAttributionTag(), "auth hatasi");
//                    }
//                }
//            });
        }

    }
}