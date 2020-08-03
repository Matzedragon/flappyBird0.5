package com.example.projetandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.util.DisplayMetrics;

public class AffichageEcran {

    private int x,y;
    private final Context mContext;
    private int texte;
    private Paint paint;
    private Paint stroke;

    public AffichageEcran(Context c, int textColor, int strokeLine) {
        mContext = c;
        paint = new Paint();
        stroke = new Paint();
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();

        stroke.setColor(strokeLine);
        stroke.setTextSize(200);
        stroke.setStrokeWidth(5);
        stroke.setAntiAlias(true);
        stroke.setStyle(Paint.Style.STROKE);

        paint.setColor(textColor);
        paint.setTextSize(200);
        paint.setAntiAlias(true);

        x = 70; // TODO create method to find how to center text
        y = metrics.heightPixels/2;
    }

    public void afficher(Canvas canvas, String texte) {

        canvas.drawText(texte,x,y, paint);
        canvas.drawText(texte,x ,y, stroke);
    }

}
