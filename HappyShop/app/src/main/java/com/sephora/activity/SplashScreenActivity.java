package com.sephora.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.sephora.app.LandingActivity;
import com.sephora.app.R;


public class SplashScreenActivity extends ActionBarActivity {

    private int SPLASH_TIME_OUT = 3000;
    private ProgressBar splashProgressBar;
    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashProgressBar = (ProgressBar) findViewById(R.id.splashscreen_progressbar);


        Thread background = new Thread(new Runnable() {
            public void run() {
                try {
                    // enter the code to be run while displaying the progressbar.
                    //
                    // This example is just going to increment the progress bar:
                    // So keep running until the progress value reaches maximum value
                    while (splashProgressBar.getProgress() <= 100) {
                        // wait 500ms between each update
                        Thread.sleep(100);

                        // active the update handler

                    }
                } catch (InterruptedException e) {
                    // if something fails do something smart
                }
            }
        });

        // start the background thread
        background.start();


        new Handler().postDelayed(new Runnable() {

          /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                splashProgressBar.setProgress(100);

                splashProgressBar.setVisibility(View.GONE);
                Uri myURI = getIntent().getData();
                Intent landingIntent = new Intent(SplashScreenActivity.this, LandingActivity.class);
                if (myURI != null) {
                    landingIntent.setData(myURI);
                }
                landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(landingIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    public void onBackPressed() {
    }
}
