package com.mlmishra.bhupendramishra.underground;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Predict extends AppCompatActivity {

    Draw_elements de;
    ImageButton camera,musiconoff,aboutus;
    SeekBar result;
    public int level,score,score_prev,new_score,pp,pp_prev,new_pp;
    public int[] r_score=new int[3];
    public String slevel,message;
    TextView progress1,s_value,pp_value,level_value,help;
    float ratio;
    MediaPlayer mp;
    Bitmap bmp;
    boolean screen_drawn=false;
    String separator = System.getProperty("line.separator");
    int versionCode;
    String versionName;
    String appname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_predict);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        de=(Draw_elements)findViewById(R.id.drawing);
        appname=getResources().getString(R.string.app_name);

        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;

        }
        catch(PackageManager.NameNotFoundException we)
        {
            versionName="1.1";
            versionCode=1;
        }


        message=appname+" v"+versionName+"("+versionCode+")"+separator+separator+"By:-"+separator+"Bhupendra Mishra"+separator+separator+"Feel free to write me"+separator+separator+"E-mail:- bhupendramishr@gmail.com"+separator+"Mob No. +91-9406903375"+separator+separator+">> HOW TO PLAY THIS GAME"+separator+separator+"1. Just com.mlmishra.bhupendramishra.predict.underground the ratio of sizes(perimeter not the area ) of Left Figure/Right Figure by sliding the sekbar to the desired value "+separator+"2. After lifting the finger from seekbar, it automatically shows the result"+separator+separator+">> FIGURES ON THE TOP OF THE SCREEN"+separator+separator+"Touch each Figures to know more about them"+separator+separator+">> Touch Camera Icon to share the score with your friends thruogh Whatsapp, Facebook, Google+, and other social media"+separator+separator+">> Touch sound icon to make sound on/off"+separator+separator+"Enjoy playing keep predicting"+separator+separator+"if you really like it please review it in play store and share it";


        progress1 =(TextView)findViewById(R.id.progress);
        s_value =(TextView)findViewById(R.id.score);
        pp_value =(TextView)findViewById(R.id.pp);
        level_value =(TextView)findViewById(R.id.level);
        help =(TextView)findViewById(R.id.help);
        help.setVisibility(View.VISIBLE);

        camera=(ImageButton)findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmp = Global.takeScreenShot(Predict.this);
                Global.saveBitmap(bmp, Predict.this);



            }
        });

        pp_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(Predict.this);
                AlertDialog alert;
                builder.setTitle("PERFECT PREDICTION [100%]");
                builder.setMessage("Number of times you made a perfect prediction of ratio");
                alert = builder.create();

                alert.show();
            }
        });


        s_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(Predict.this);
                AlertDialog alert;
                builder.setTitle("SCORE");
                builder.setMessage("Total Score Achieved for the number of levels played in the game");
                alert = builder.create();

                alert.show();
            }
        });


        level_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(Predict.this);
                AlertDialog alert;
                builder.setTitle("LEVEL");
                builder.setMessage("Progress of the game, number shows you played the game these number of times");
                alert = builder.create();

                alert.show();
            }
        });


        musiconoff=(ImageButton)findViewById(R.id.musiconoff);
        if(Global.music_on_command)
            musiconoff.setImageResource(getResources().getIdentifier("musicon", "drawable", getPackageName()));
        else
            musiconoff.setImageResource(getResources().getIdentifier("musicoff", "drawable", getPackageName()));


        musiconoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.music_on_command) {
                    Global.music_on_command = false;
                } else {
                    Global.music_on_command = true;
                }

                if(mp.isPlaying() && !Global.music_on_command)
                {
                    mp.pause();
                    musiconoff.setImageResource(getResources().getIdentifier("musicoff", "drawable", getPackageName()));
                }
                if(!mp.isPlaying() && Global.music_on_command) {
                    mp.start();
                    musiconoff.setImageResource(getResources().getIdentifier("musicon", "drawable", getPackageName()));
                }

            }
        });


        aboutus=(ImageButton)findViewById(R.id.aboutus);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Predict.this);
                AlertDialog alert;
                builder.setTitle("ABOUT US");
                builder.setMessage(message);
                alert=builder.create();

                alert.show();

            }
        });


        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        level = prefs.getInt("level",1);
        score_prev = prefs.getInt("score",0);
        pp_prev=prefs.getInt("pp",0);
        s_value.setText("Score: "+String.valueOf(score_prev));
        level_value.setText("Level: "+String.valueOf(level));
        pp_value.setText("PP: "+String.valueOf(pp_prev));


        mp = MediaPlayer.create(this, R.raw.ring);
        mp.setLooping(true);
        if(Global.music_on_command) {
            mp.start();
            mp.seekTo(0);
        }




        Timer t = new Timer(false);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        help.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 15000);



        //de.changeColor();
        if(!Global.screen_drawn) {
            de.invalidate();
        }
        result = (SeekBar) findViewById(R.id.seek1);

        result.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                ratio=(float)((float)progress/10.0);
                progress1.setText(String.valueOf(ratio));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                r_score=Global.result(Global.get_rad1(),Global.get_rad2(),ratio);
                Global.screen_drawn=false;
                score=r_score[0];
                pp=r_score[1];
                if(score==100) {
                    Toast.makeText(Predict.this, "PERFECT MATCH +1 for perfect match & +100 in Score", Toast.LENGTH_LONG).show();
                }
                else if(score>80)
                {
                    Toast.makeText(Predict.this, "Fairy good Match, Compare Score is : " + String.valueOf(score), Toast.LENGTH_LONG).show();

                }
                else if(score>70)
                {
                    Toast.makeText(Predict.this, "good Match, Compare Score is : " + String.valueOf(score), Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(Predict.this, "Concentrate and improve your prediction, Compare Score is : " + String.valueOf(score) , Toast.LENGTH_LONG).show();

                }
                Log.d("score", String.valueOf(score));
                Log.d("rad1", String.valueOf(Global.get_rad1()));
                Log.d("rad2",String.valueOf(Global.get_rad2()));
                new_score=score+score_prev;
                new_pp=pp+pp_prev;
                level=level+1;
                Log.d("level",String.valueOf(level));
                slevel=String.valueOf(level)+"#";
                SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("level", level);
                editor.putInt("score",new_score);
                editor.putInt("pp",new_pp);
                editor.commit();
                if(mp.isPlaying()) {
                    mp.pause();
                }
                Intent i = new Intent(getApplicationContext(), Level.class);
                // sending data to new activity
                i.putExtra("product", slevel);
                startActivity(i);
                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_predict, menu);
        return true;
    }


    @Override
    public void onPause() {
        super.onPause();
         // Always call the superclass method first
        if(mp.isPlaying()) {
            mp.pause();
        }
        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
      /*  if(mp.isPlaying()) {
            mp.reset();
        }*/

    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        if(!mp.isPlaying() && Global.music_on_command) {
            mp.start();
        }

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
