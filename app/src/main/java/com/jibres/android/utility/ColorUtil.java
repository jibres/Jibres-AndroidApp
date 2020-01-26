package com.jibres.android.utility;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ColorUtil {

    public static void setGradient(View view, String START , String END){

        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {
                        Color.parseColor(START),
                        Color.parseColor(END)
                });
        view.setBackgroundDrawable(gradient);
    }

    public static void setGradient(View view, String START , String END, Boolean setLeftToRight){
        if (setLeftToRight){
            GradientDrawable gradient = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[] {
                            Color.parseColor(START),
                            Color.parseColor(END)
                    });
            view.setBackgroundDrawable(gradient);
        }else {
         setGradient(view,START,END);
        }
    }

    public static void setGradient(TextView textView, String START , String END){
        textView.measure(0, 0);       //must call measure!
        int h = textView.getMeasuredHeight(); //get height
        int w = textView.getMeasuredWidth()/2;  //get width
        Log.e("amingoli", h+"setGradient: "+w );
        textView.getPaint().setShader(
                new LinearGradient(0, 0, w, h,
                Color.parseColor(START),
                Color.parseColor(END),
                Shader.TileMode.CLAMP));
    }
}
