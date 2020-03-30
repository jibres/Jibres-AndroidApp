package com.jibres.android.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class GradientTextView extends TextView {
    // ===========================================================
    // Constants
    // ===========================================================

    public static final int LG_HORIZONTAL = 101;
    public static final int LG_VERTICAL = 102;

    // ===========================================================
    // Fields
    // ===========================================================

    private boolean mApplyLinearGradient = false;
    private int mGradientOrientation = LG_HORIZONTAL;
    private int mStartColor = Color.BLACK;
    private int mEndColor = Color.BLACK;

    // ===========================================================
    // Constructors
    // ===========================================================

    public GradientTextView(Context context) {
        super(context);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // ===========================================================
    // Overriden Methods
    // ===========================================================

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mApplyLinearGradient)
            applyLinearGradient();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void applyLinearGradient() {
        Shader shader = null;

        if (mGradientOrientation == LG_HORIZONTAL) {
            shader = new LinearGradient(0, getHeight(), getWidth(), 0, mStartColor, mEndColor, Shader.TileMode.CLAMP);
        } else {
            shader = new LinearGradient(0, 0, 0, getHeight(), mStartColor, mEndColor, Shader.TileMode.CLAMP);
        }

        getPaint().setShader(shader);
    }


    /**
     * Simple method to set linear gradient to textview with orientation as Horizontal or Vertical
     */
    public void setLinearGradient(int mStartColor, int mEndColor, int mGradientOrientation) {
        this.mApplyLinearGradient = true;
        this.mStartColor = mStartColor;
        this.mEndColor = mEndColor;
        this.mGradientOrientation = mGradientOrientation;

        // Request for refresh
        requestLayout();
    }

    public void setLinearGradient(int mStartColor, int mEndColor) {
        this.mApplyLinearGradient = true;
        this.mStartColor = mStartColor;
        this.mEndColor = mEndColor;
        this.mGradientOrientation = LG_HORIZONTAL;

        // Request for refresh
        requestLayout();
    }

}