package com.example.samharris.task2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

/**
 * Created by Sam Harris on 12-06-2016.
 */
public class MyView extends View {
    private Drawable image;

    public MyView(Context context) {

        super(context);

        image = new ShapeDrawable(new RectShape());
        image.setBounds(0, 0, 100, 100);
        ((ShapeDrawable) image).getPaint().setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        image.draw(canvas);
    }

    public void move(int x, int y) {
        Rect bounds = image.getBounds();
        bounds.left += x;
        bounds.right += x;
        bounds.top += y;
        bounds.bottom += y;
        invalidate();
    }
}