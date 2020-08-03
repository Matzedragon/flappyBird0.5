package com.example.projetandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

public class Bird {

    private BitmapDrawable img=null;
    private int x,y; // coordonnées x,y de l'oiseau
    private int TailleLongueur, TailleLargeur; // largeur et hauteur de l'oiseau en pixel


    // variables pour le saut de l'oiseau
    private double gravity = -1.7;
    private double velocity = 0.0;

    private final Context mContext;

    // l'oiseau est-il entrain de sauter
    private boolean saut = false;

    private int tailleEcranX, tailleEcranY;

    // la partie est-elle perdu
    private boolean mort;

    public Bird(final Context c) {
        mContext=c;
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        tailleEcranX = metrics.widthPixels;
        tailleEcranY=metrics.heightPixels;
        // taille de l'image en % de l'écran
        TailleLongueur = (int)(((5*tailleEcranY/100)));
        TailleLargeur = (int)(((10*tailleEcranX/100)));
        // placement de l'oiseau au centre du téléphone
        y = metrics.heightPixels/2- TailleLongueur/2;
        x = metrics.widthPixels/2 - TailleLargeur/2;
        mort = false;
    }

    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }


    public void resize(int wScreen, int hScreen) {
        img = setImage(mContext,R.mipmap.floppy_bird,TailleLargeur,TailleLongueur);
    }

    public void setSaut(boolean doitSauter) {
        saut = doitSauter;
        if(saut) {
            velocity = 15;
        }
    }

    public void jump() {
        int margeBasse = 10*tailleEcranY/100;
        if (saut && !mort) {
            velocity -= gravity;
            y -= velocity;
            if (y <= 0) {
                y = 0;
                velocity = 0.0;
            }

        } else {
            velocity += gravity;
            y -= velocity;
            if (y >= tailleEcranY - margeBasse) {
                y = tailleEcranY - margeBasse;// arrivé en bas
                velocity = 0.0;
            }
        }
    }
    public int getTailleEcranX() {return tailleEcranX;}
    public int getTailleEcranY() {return tailleEcranY;}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getMort() {return mort;}

    public int getTailleLongueur() {
        return TailleLongueur;
    }

    public int getTailleLargeur() {
        return TailleLargeur;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setMort(boolean mort) {
        this.mort = mort;
    }

    // on dessine l'oiseau, en x et y
    public void draw(Canvas canvas)
    {
        if(img==null) {return;}
        canvas.drawBitmap(img.getBitmap(), x, y, null);
    }

}
