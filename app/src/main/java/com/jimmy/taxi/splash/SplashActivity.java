package com.jimmy.taxi.splash;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.jimmy.taxi.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            final AnimatedVectorDrawable anim = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim);
            final ImageView logo = findViewById(R.id.logo);
            logo.setImageDrawable(anim);
            anim.start();
        }
    }
}
