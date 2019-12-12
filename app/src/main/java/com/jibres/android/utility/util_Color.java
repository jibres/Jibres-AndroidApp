package com.jibres.android.utility;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

public class util_Color {

    public static void gradientDrawable(View view, String START , String END){

        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {
                        Color.parseColor(START),
                        Color.parseColor(END)
                });
        view.setBackgroundDrawable(gradient);
    }
}
