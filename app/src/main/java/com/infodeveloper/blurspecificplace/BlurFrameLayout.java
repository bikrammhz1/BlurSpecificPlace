package com.infodeveloper.blurspecificplace;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.LinearLayout;

import static android.graphics.Bitmap.createScaledBitmap;

public class BlurFrameLayout extends LinearLayout {

    Bitmap resizedBitmap;
    int offset = 0;

    private int index;
    private int pos;
    private int color;

    public BlurFrameLayout(Context context) {

        super(context);
        callDelayedUpdate();
    }

    public BlurFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final Handler handler = new Handler();
        callDelayedUpdate();
    }

    public BlurFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        callDelayedUpdate();
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private void callDelayedUpdate() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawBlurImage();
            }
        }, 50);
    }

    public void drawBlurImage() {

        if (resizedBitmap == null) {
            resizedBitmap = Bitmap.createBitmap(MainActivity.blurredBitmap, dpToPx(16), dpToPx(16), getWidth(), getHeight());
            resizedBitmap = Bitmap.createScaledBitmap(resizedBitmap, this.getWidth(), this.getHeight(), true);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (resizedBitmap != null) {
           if(index==pos) {
               canvas.drawBitmap(resizedBitmap, offset, 0, null);
           }else{
               canvas.drawBitmap(resizedBitmap, offset -(getWidth()+  dpToPx(32)), 0, null);
           }

        }
        canvas.drawColor(color);
        super.onDraw(canvas);
    }

    public void updateView(int offset,int pos) {

        this.offset = offset;

        this.pos = pos;


        invalidate();
    }

    public void setIndex(int index) {

        this.index = index;
    }
    public void setColor(int color) {

        this.color = color;
    }
}
