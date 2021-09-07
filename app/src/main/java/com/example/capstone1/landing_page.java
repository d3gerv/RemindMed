package com.example.capstone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class landing_page extends AppCompatActivity {


    Animation topAnim;
    ImageView image;
    private ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_landing_page);

        button = (ImageView) findViewById(R.id.nextBtn);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);

        image = findViewById(R.id.logo);

        image.setAnimation(topAnim);

        ImageView button = (ImageView) findViewById(R.id.nextBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmain();
            }
        });
    }

    private void openmain() {
        Intent intent = new Intent(this, main_page.class);
        startActivity(intent);
    }
}