package com.mlmishra.bhupendramishra.underground;

/**
 * Created by bhupendramishra on 17/09/15.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;



public class Draw_elements extends View
{
    public int distance=80,option;
    Random r1,r2,r3,r4;

    private Paint circlePaint, canvasPaint;
    //initial color

    private int circleCol, labelCol;

    public Draw_elements(Context context, AttributeSet attrs){
        super(context, attrs);
        r1 = new Random();
        r2 = new Random();
        r3 = new Random();
        r4 = new Random();
        circlePaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Draw_elements, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            circleCol = a.getInteger(R.styleable.Draw_elements_circleColor, 0);//0 is default

        } finally {
            a.recycle();
        }


    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width =getWidth();
        int height=getHeight();


        circlePaint.setStyle(Style.FILL);
        //Log.d("circle paint", "circle painted");
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleCol);
        option=r3.nextInt(4)+1;
        Global.screen_drawn=true;
        Log.d("option",String.valueOf(option));
        if(option==1) {
            int rand_radius1=r1.nextInt(40)+30;
            int rand_radius2=r2.nextInt(40)+10;
            Global.set_rad1(rand_radius1);
            Global.set_rad2(rand_radius2);
            canvas.drawCircle((float)width / 2 - (float)distance, (float)height / 2, (float)rand_radius1, circlePaint);
            canvas.drawCircle((float)width / 2 + (float)distance, (float)height / 2, (float)rand_radius2, circlePaint);
            Log.d("circle paint", "circle painted");
            Log.d("rad1_circle", String.valueOf((float)rand_radius1));
            Log.d("rad2_circle",String.valueOf((float)rand_radius2));
        }
        else if(option==2)
        {
            int rand_radius1=r1.nextInt(80)+30;
            int rand_radius2=r2.nextInt(80)+10;
            Global.set_rad1(rand_radius1);
            Global.set_rad2(rand_radius2);
            canvas.drawRect((float) (width / 2) - (float) distance - (float) ((rand_radius1) / 2), (float) (height / 2) - (float) ((rand_radius1 ) / 2), (float) (width / 2) - (float) (distance) + (float) ((rand_radius1 ) / 2), (float) (height / 2) + (float) ((rand_radius1 ) / 2), circlePaint);
            canvas.drawRect((float) (width / 2) + (float) distance - (float) ((rand_radius2) / 2), (float) (height / 2) - (float) ((rand_radius2 ) / 2), (float) (width / 2) + (float) distance + (float) ((rand_radius2 ) / 2), (float) (height / 2) + (float) ((rand_radius2 ) / 2), circlePaint);
            Log.d("Rectangle", "Rectangle painted");
            Log.d("rad1_rect", String.valueOf((float)rand_radius1));
            Log.d("rad2_rect",String.valueOf((float)rand_radius2));

        }
        else if(option==3)
        {
            int rand_radius1=r1.nextInt(80)+30;
            int rand_radius2=r2.nextInt(80)+10;
            Global.set_rad1(rand_radius1 );
            Global.set_rad2(rand_radius2 );
            canvas.drawLine((float) (width / 2) - (float) distance, (float) (height / 2) - (float) ((rand_radius1 ) / 2), (float) (width / 2) - (float) (distance), (float) (height / 2) + (float) ((rand_radius1 ) / 2), circlePaint);
            canvas.drawLine((float) (width / 2) + (float) distance, (float) (height / 2) - (float) ((rand_radius2 ) / 2), (float) (width / 2) + (float) distance, (float) (height / 2) + (float) ((rand_radius2 ) / 2), circlePaint);
            Log.d("line", "line painted");
            Log.d("rad1_line", String.valueOf((float)rand_radius1));
            Log.d("rad2_line",String.valueOf((float)rand_radius2));
            }
        else if(option==4)
        {
            int char_p=r4.nextInt(94)+33;
            int rand_radius1=r1.nextInt(80)+30;
            int rand_radius2=r2.nextInt(80)+10;
            String p;
            Global.set_rad1(rand_radius1);
            Global.set_rad2(rand_radius2);
            p=Character.toString ((char) char_p);


            Log.d("Chartoprint",p);
            //canvas.drawLine((float) (width / 2) - (float) distance, (float) (height / 2) - (float) ((rand_radius1 + 30) / 2), (float) (width / 2) - (float) (distance), (float) (height / 2) + (float) ((rand_radius1 + 30) / 2), circlePaint);
            //canvas.drawLine((float) (width / 2) + (float) distance, (float) (height / 2) - (float) ((rand_radius2 + 10) / 2), (float) (width / 2) + (float) distance, (float) (height / 2) + (float) ((rand_radius2 + 10) / 2), circlePaint);
            circlePaint.setTextSize(rand_radius1);
            canvas.drawText(p, (float) (width / 2) - (float) distance, (float) (height / 2), circlePaint);
            circlePaint.setTextSize(rand_radius2);
            canvas.drawText(p, (float) (width / 2) + (float) distance, (float) (height / 2), circlePaint);
            Log.d("rad1_char", String.valueOf((float)rand_radius1));
            Log.d("rad2_char", String.valueOf((float)rand_radius2));
        }


    }


}
