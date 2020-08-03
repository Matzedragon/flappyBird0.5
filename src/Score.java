package com.example.projetandroid;

import android.content.Context;
import android.util.DisplayMetrics;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

public class Score {

    //position du score sur l'écran
    private int x,y;
    private final Context mContext;
    private int scoreJ;
    private Paint paint;
    private Paint stroke;

    public Score(final Context c) {
        mContext=c;
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();

        scoreJ = 0;

        stroke = new Paint();
        stroke.setColor(Color.BLACK);
        stroke.setTextSize(200);
        stroke.setAntiAlias(true);
        stroke.setStrokeWidth(5);
        stroke.setStyle(Paint.Style.STROKE);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(200);
        paint.setAntiAlias(true);

        float w = paint.measureText(String.valueOf(scoreJ)); // taille de la string du score pour
        x =  metrics.widthPixels/2 - (int) (w/2);          // placer le score au milieu de l'écran
        y = metrics.heightPixels/6;
    }

    public void addToScore() {
        scoreJ+=1;
    }

    public void draw(Canvas canvas) {
        canvas.drawText(String.valueOf(scoreJ),x,y, paint);
        canvas.drawText(String.valueOf(scoreJ),x ,y, stroke);
    }

    public String toString() {
        return String.valueOf(scoreJ);
    }
}
