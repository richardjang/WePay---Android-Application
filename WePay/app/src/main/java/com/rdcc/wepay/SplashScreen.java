package com.rdcc.wepay;

//Animated screen that displays our logo

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class SplashScreen extends Activity{
//    MediaPlayer bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //we need to initialize parse for us
        Parse.initialize(this, "uAGZNC52nB88M9xMSFaa89hEPQuCAn6vAozFrunJ", "XqKm1Xi6Y4apeHFbLKzW7xKxbOFJHRxNYv79cHvU");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

        setContentView(R.layout.layout_splash);

//        bgm = MediaPlayer.create(this, R.raw.splashbgm);
//        bgm.start();

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    bgm.release();
                    // start activity
                    Intent myIntent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(myIntent);
                    finish();
                }
            }
        };
        timer.start();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//        bgm.release();
        finish();
    }
}
