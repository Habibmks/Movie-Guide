package com.example.movieguide;

import androidx.annotation.NonNull;
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
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button login = (Button) findViewById(R.id.loginbtn);
        final Button toregbtn = (Button) findViewById(R.id.logtoregbtn);
        final EditText logemail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        final EditText logpass = (EditText) findViewById(R.id.editTextTextPassword);
        final TextView tverror = (TextView) findViewById(R.id.textView2);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                auth.signInWithEmailAndPassword(logemail.getText().toString(),logpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Giriş başarılı",Toast.LENGTH_SHORT);
                            Log.d("Sign in","Signed in Succesfully");
                            Intent intent = new Intent(MainActivity.this,Test.class);
                            String id = auth.getCurrentUser().getUid().toString();
                            db.collection("Users").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    user = documentSnapshot.toObject(User.class);
                                    Log.d("Firebase get","User's name" + user.getName());
                                    intent.putExtra("user",user);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Firestore Read",e.getMessage().toString());
                                }
                            });

                        }else{
                            Toast.makeText(MainActivity.this,"Hatalı giriş",Toast.LENGTH_SHORT);
                            Log.d("Sign in","Couldn't sign in");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Sign in Failure",e.getMessage().toString());
                    }
                });
            }
        });
        logemail.setText("qwe@gmail.com");
        logpass.setText("qweasd123");
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
        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(tverror);
            }
        });*/
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