package com.mlmishra.bhupendramishra.underground;

/**
 * Created by bhupendramishra on 17/09/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Global {
    public static int r1,r2;
    public static int get_rad1()
    {
        return r1;
    }
    public static int get_rad2()
    {
        return r2;
    }
    public static boolean screen_drawn=false;
    public static boolean music_on_command=true;
    public static void set_rad1(int rad1)
    {

        r1=rad1;
    }
    public static void set_rad2(int rad2)
    {
       r2=rad2;
    }

    public static int[] result(int rad1, int rad2, float byuser)
    {
        int[] score=new int[3];
        int penalty=20;
        float v1,v2;
        v1=(float)rad1/(float)rad2;
        v2=byuser/v1;
        Log.d("v1",String.valueOf(v1));
        Log.d("v2",String.valueOf(v2));
        if(v2> 0.9 && v2<1.1)
            score[0]=100;
        else if(v2>1.1 && v2<1.5)
            score[0]=(int)((2-v2)*100-penalty);
        else if(v2<0.9 && v2>0.5)
            score[0]=(int)((v2*100)-penalty);
        else
            score[0]=0;
        if(score[0]==100)
            score[1]=score[1]+1;
        return score;
    }

    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        Log.e("Screenshot", "taken successfully");
        return b;

    }

    public static void saveBitmap(Bitmap bitmap, Activity activity) {
        File imagePath = new File(Environment.getExternalStorageDirectory()
                + "/screenshot.png");
        String path =Environment.getExternalStorageDirectory() + "/screenshot.png";
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(CompressFormat.JPEG, 100, fos);
            Log.e("Screenshot", "saved successfully");

            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }

        Toast.makeText(activity,"Screenshot taken successfully, file saved as "+path,Toast.LENGTH_LONG).show();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/png");
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///"+imagePath));

        /** START ACTIVITY **/
        activity.startActivity(Intent.createChooser(share,"Share Image"));
    }
}
