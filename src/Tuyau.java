package com.example.projetandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

public class Tuyau {
    //offset image
    private int xHaut,yHaut, xBas, yBas;
    // Taille de l'image
    private int sizeX, sizeYHaut, sizeYBas;
    private int tailleEcranX, tailleEcranY;
    // écart entre 2 tubes
    private final int ECART;
    // utilisé pour savoir dans quel range les tuyaux peuvent bouger
    private int decalage = 100;
    private BitmapDrawable imgHaut=null;
    private BitmapDrawable imgBas=null;

    // définition de la vitesse d'un tuyau
    private static final int INCREMENTX = 7;
    private static final int INCREMENTY = 7;
    private int speedX=INCREMENTX, speedY=INCREMENTY;

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image de la balle
    private final Context mContext;

    public Tuyau(final Context c){
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        tailleEcranY = metrics.heightPixels;
        tailleEcranX = metrics.widthPixels;
        mContext=c;
        ECART =  25*tailleEcranY/100;

        yHaut = 0;
        xHaut = metrics.widthPixels;;
        yBas =0;
        xBas = metrics.widthPixels;;

    }

    // on attribue à l'objet tuyau l'image passée en paramètre
    // l'image sera soit tournée vers le bas si placement == bas, soit vers le haut si placement == haut
    // w et h sont sa largeur et hauteur définis en pixels
    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    public void resize(int wScreen, int hScreen) {
        // Taille de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter si le tuyau sort de l'écran
        tailleEcranX=wScreen;
        tailleEcranY=hScreen;
        // on définit (au choix) la taille de la balle à 1/5ème de la largeur de l'écran

        sizeYHaut=wScreen;
        sizeYBas=wScreen;
        sizeX= hScreen/7;
        setRandomSize(); // génére une taille aléatore pour le tuyau du haut
                        // ( qui entrainera une taille pour celui du bas)
        imgHaut = setImage(mContext,R.mipmap.tuyau_haut,sizeX,sizeYHaut);
        imgBas = setImage(mContext,R.mipmap.tuyau_bas,sizeX,sizeYBas);
    }

    public void move(Bird oiseau) {

        if (((xHaut-=speedX) <= oiseau.getX()+oiseau.getTailleLargeur()
                && xHaut+sizeX >= oiseau.getX()+oiseau.getTailleLargeur()
                && (yHaut+sizeYHaut) >= oiseau.getY())

            ||(xBas -= speedX) <= oiseau.getX()+oiseau.getTailleLargeur()
                && xBas+sizeX >= oiseau.getX()+oiseau.getTailleLargeur()
                && (yBas) <= oiseau.getY()+oiseau.getTailleLongueur()) {
            oiseau.setMort(true);
            speedX=0;
        }

        // on incrémente les coordonnées X
        if (xHaut <= 0 - sizeX) {
            setRandomSize();
            xHaut = tailleEcranX;
            xBas = tailleEcranX;
        }

    }

    // on dessine le tuyau, en x et y
    public void draw(Canvas canvas)
    {
        if(imgHaut==null) {return;}
        canvas.drawBitmap(imgHaut.getBitmap(), xHaut, yHaut, null);
        if(imgBas==null) {return;}
        canvas.drawBitmap(imgBas.getBitmap(), xBas, yBas, null);
    }

    public void setRandomSize(){
        int randomizeTube = (int)(Math.random()*( 1000 - 50 ));
        yHaut = 0-randomizeTube;
        yBas = (yHaut + sizeYHaut)+ECART;
    }
    public int getxHaut() {
        return xHaut;
    }

    public int getyHaut() {
        return yHaut;
    }

    public int getxBas() {
        return xBas;
    }

    public int getyBas() {
        return yBas;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeYHaut() {
        return sizeYHaut;
    }

    public int getSizeYBas() {
        return sizeYBas;
    }

    public int getSpeedX() {
        return speedX;
    }
}
