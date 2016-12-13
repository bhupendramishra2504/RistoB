package com.mlmishra.bhupendramishra.underground;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

public class Level extends AppCompatActivity {
    String level;
    TextView levelshower;
    Handler handler;
    MediaPlayer mp;
    protected AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
    protected AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_level);
        levelshower=(TextView)findViewById(R.id.levelshower);

            // Saving integer

        Intent i = getIntent();
        // getting attached intent data
        level= i.getStringExtra("product");
        Log.d("level",level);
        levelshower.setText(level);

        levelshower.startAnimation(fadeIn);
        levelshower.startAnimation(fadeOut);
        //fadeIn.setDuration(1200);
        //fadeIn.setFillAfter(true);
        fadeOut.setDuration(2000);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(1200);
        mp = MediaPlayer.create(this, R.raw.ring);
        mp.setLooping(true);
        if(Global.music_on_command) {
            if (!mp.isPlaying()) {
                mp.start();
                mp.seekTo(0);
            }
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app Next activity
                if(mp.isPlaying()) {
                    mp.pause();
                }
                Intent i = new Intent(Level.this, Predict.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 3000);
        //Intent i1 = new Intent(getApplicationContext(), Predict.class);
       // startActivity(i1);
       // finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
