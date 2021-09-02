package com.example.capstone1;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeSession extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth rootAuthen = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = rootAuthen.getCurrentUser();

        if(firebaseUser !=null){
            Intent intent = new Intent(HomeSession.this, home_page.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //startActivity(new Intent(HomeSession.this, home_page.class));
        }
    }
}
