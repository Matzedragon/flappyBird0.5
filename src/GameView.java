package com.example.projetandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

// SurfaceView est une surface de dessin.
// référence : https://developer.android.com/reference/android/view/SurfaceView
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private GameLoopThread gameLoopThread;
    private Tuyau tuyau;
    private Bird bird;
    private Bitmap background;
    private Bitmap scaled;
    private int compteur;
    private Score score;
    private boolean gamePaused;
    private final MediaPlayer sound = MediaPlayer.create(getContext() ,R.raw.jump);

    // création de la surface de dessin
    public GameView(Context context) {
        super(context);
        int height= context.getResources().getDisplayMetrics().heightPixels;
        getHolder().addCallback(this);
        gameLoopThread = new GameLoopThread(this);
        background = BitmapFactory.decodeResource(getResources(),R.drawable.background1);

        scaled = Bitmap.createScaledBitmap(background, background.getWidth(), height, true);
        // création des objets
        tuyau = new Tuyau(this.getContext());
        bird = new Bird(this.getContext());
        score = new Score(this.getContext());
        gamePaused = true; // par défaut à la création le jeu est en pause
    }

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if(canvas==null) {return;}
        // on efface l'écran, en blanc
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(scaled,-200,0,null);
        // on dessine la balle
        //balle.draw(canvas);
        tuyau.draw(canvas);
        bird.draw(canvas);
        score.draw(canvas);
        MainActivity myContext = MainActivity.getContext();
        if(bird.getMort()) {
            gameLoopThread.setRunning(false);
            myContext.pauseSong();
            myContext.menuFin(score.toString());
        } else {
            myContext.startSong();
        }
    }

    // Fonction appelée par la boucle principale (gameLoopThread)
    // On gère ici le déplacement des objets
    public void update() {
        if(gamePaused != true) {
            bird.jump();
            tuyau.move(bird);
            changeScore();
        }
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus GameLoopThread si cela n'est pas fait
        if(gameLoopThread.getState()==Thread.State.TERMINATED) {
            gameLoopThread=new GameLoopThread(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée juste avant que l'objet ne soit détruit.
    // on tente ici de stopper le processus de gameLoopThread
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }

    // Gère les actions lorsque l'écran est touché.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int)event.getX();
        int currentY = (int)event.getY();

        switch (event.getAction()) {
            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                // on enlève la possibilité de faire sauter l'oiseau s'il est mort
                if(!bird.getMort()) {
                    bird.setSaut(true);
                    sound.start();
                }
                // si le jeu est en pause au début du jeu
                if(gamePaused == true) {
                    gamePaused = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                bird.setSaut(false);
                break;
        }

        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }

    // incrémente le score et l'update lorsque l'oiseau passe un tuyau
    public void changeScore() {
        if(tuyau.getSizeX()+tuyau.getxHaut() < bird.getX()
          && tuyau.getSizeX()+tuyau.getxHaut() > bird.getX()-tuyau.getSpeedX()) {
            score.addToScore();
        }
    }


    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION, MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        tuyau.resize(w,h);
        bird.resize(w,h);
    }
}