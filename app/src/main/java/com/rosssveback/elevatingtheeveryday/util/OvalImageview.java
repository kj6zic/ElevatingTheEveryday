package com.rosssveback.elevatingtheeveryday.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ankit on 8/21/15.
 */
public class OvalImageview extends ImageView {

    private int radius = 10;

    public OvalImageview(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        clipPath.addRoundRect(new RectF(0, 0, w, h), radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }

    public OvalImageview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public OvalImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        this.invalidate();
    }
}
