package com.example.movieguide.Functions;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.movieguide.Firebase.Authentication;
import com.example.movieguide.MainActivity;
import com.example.movieguide.RegisterActivity;
import com.example.movieguide.Test;
import com.example.movieguide.collections.User.User;

public class Functions {

    static public boolean verifreset(String mail,Context context){
        if (!mail.contains("@")){
            Toast.makeText(context,"Invalid email",Toast.LENGTH_SHORT);
            MainActivity.error = 0;
            return false;
        }
        return true;
    }


    static public boolean verifregis(String name,String sname,String uname,String mail,String pass,String pass2,Context context){
        if (name.isEmpty()){
            Toast.makeText(context,"Name field is empty",Toast.LENGTH_SHORT).show();
            RegisterActivity.error = 0;
            return false;
        }else if (sname.isEmpty()){
            Toast.makeText(context,"Surname field is empty",Toast.LENGTH_SHORT).show();
            RegisterActivity.error = 1;
            return false;
        }else if (uname.isEmpty()){
            Toast.makeText(context,"Username field is empty",Toast.LENGTH_SHORT).show();
            RegisterActivity.error = 2;
            return false;
        }else if (mail.isEmpty()){
            Toast.makeText(context,"Email field is empty",Toast.LENGTH_SHORT).show();
            RegisterActivity.error = 3;
            return false;
        }else if (pass.isEmpty()){
            Toast.makeText(context,"Password field is empty",Toast.LENGTH_SHORT).show();
            RegisterActivity.error = 4;
            return false;
        }else if(pass.length()<8){
            Toast.makeText(context,"Password must be longer than 7 character",Toast.LENGTH_SHORT).show();
            RegisterActivity.error = 4;
            return false;
        } else if (pass2.isEmpty()){
            Toast.makeText(context,"Password field is empty",Toast.LENGTH_SHORT).show();
            RegisterActivity.error = 5;
            return false;
        }else if (uname.length()<6){
            Toast.makeText(context,"Username should be longer than 6 character",Toast.LENGTH_SHORT).show();
            RegisterActivity.error =2;
            return false;
        }else if (!mail.contains("@") && !mail.contains(".com")){
            Toast.makeText(context,"Email is not valid",Toast.LENGTH_SHORT).show();
            RegisterActivity.error = 3;
            return false;
        }else if(!pass.equals(pass2)){
            Toast.makeText(context,"Passwords are not matched",Toast.LENGTH_SHORT).show();
            RegisterActivity.error = 5;
            return false;
        }
        return true;
    }
    static public boolean veriflogin(String mail,String password,Context context){
        if(mail.isEmpty()){
            Toast.makeText(context,"Email field is empty",Toast.LENGTH_SHORT).show();
            MainActivity.error = 0;
            return false;
        }else if(password.isEmpty()){
            Toast.makeText(context,"Password field is empty",Toast.LENGTH_SHORT).show();
            MainActivity.error = 1;
            return false;
        }else if(!mail.contains("@") && !mail.contains(".com")){
            Toast.makeText(context,"Email is not valid",Toast.LENGTH_SHORT).show();
            MainActivity.error = 0;
            return false;
        }else return true;
    }
    static public void reset(String mail,Context context){
        Authentication auth = new Authentication(context);
        auth.forgetpassword(mail, new Authentication.forgetpass() {
            @Override
            public void onResponse(Boolean bool) {
                Toast.makeText(context,bool ? "Mail sent":"Failed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                if (message.equals("The email address is badly formatted.")){
                    Toast.makeText(context,"Invalid Email",Toast.LENGTH_SHORT).show();
                }else if (message.equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                    Toast.makeText(context,"There is no account linked with email",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    static public void login(String mail,String password,Context context){
        Authentication auth = new Authentication(context);
        auth.Login(mail, password, new Authentication.login() {
            @Override
            public void onError(String message) {
                Log.d("Sign in Error", message);
                if (message.equals("Auth The email address is badly formatted.")){
                    Toast.makeText(context,"Email is not valid",Toast.LENGTH_SHORT).show();
                }else if(message.equals("Auth The password is invalid or the user does not have a password.")){
                    Toast.makeText(context,"Wrong password",Toast.LENGTH_SHORT).show();
                }else if(message.equals("Auth There is no user record corresponding to this identifier. The user may have been deleted.")){
                    Toast.makeText(context,"There is no account",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(User user) {
                Intent intent = new Intent(context, Test.class);
                intent.putExtra("user", user);
                intent.putExtra("userid", auth.getAuth().getCurrentUser().getUid());
                context.startActivity(intent);
            }
        });
    }

    static public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi !=null && wifi.isConnected()) || (mobile !=null && mobile.isConnected())){
            return true;
        }else return false;
    }

    public static String genres(int id){
        switch (id){
            case 28:
                return "Action";
            case 12:
                return "Adventure";
            case 16:
                return "Animation";
            case 35:
                return "Comedy";
            case 80:
                return "Crime";
            case 99:
                return "Documentary";
            case 18:
                return "Drama";
            case 10751:
                return "Family";
            case 14:
                return "Fantasy";
            case 36:
                return "History";
            case 27:
                return "Horror";
            case 10402:
                return "Music";
            case 9648:
                return "Mystery";
            case 10749:
                return "Romance";
            case 878:
                return "Science Fiction";
            case 10770:
                return "TV Movie";
            case 53:
                return "Thriller";
            case 10752:
                return "War";
            case 37:
                return "Western";
            default:
                return null;
        }
    }
}
