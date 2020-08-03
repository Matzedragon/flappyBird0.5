package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    static private MainActivity myCurrentActivity;
    private GameView gameView;

    /* Attributs */
    private Button boutonJouer;
    private ImageButton boutonOptions;
    private ImageButton boutonCustom;
    private MediaPlayer music;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        music = MediaPlayer.create(this,R.raw.myusik);
        setContentView(R.layout.menu);
        //permet de récuérer le context
        myCurrentActivity = this;

        /* Déclaration des éléments */
        boutonJouer = (Button) findViewById(R.id.boutonJouer);
        boutonOptions = (ImageButton) findViewById(R.id.boutonOptions);
        boutonCustom = (ImageButton) findViewById(R.id.boutonCustom);

        // On créé un objet "GameView" qui correspond à l'affichage
        // de l'écran de jeu
        gameView=new GameView(this);

        // on lance la musique du jeu
        startSong();

        /* On appuie sur le bouton JOUER */
        boutonJouer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // on affiche l'écran de jeu.
                lancementJeu();
            }
        });

        /* On appuie sur le bouton OPTIONS */
        boutonOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // options
            }
        });

        /* On appuie sur le bouton CUSTOM */
        boutonCustom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Renvoie sur la page de choix du perso
            }
        });
    }

    /**
     * Lance l'activité du Menu de fin du jeu ( game over )
     * @param scoreFin
     */
    public void menuFin(String scoreFin) {
        Intent intent = new Intent(this, MenuFin.class);
        intent.putExtra("score", scoreFin);
        startActivity(intent);
        finish();
    }

    public void lancementJeu() {
        setContentView(gameView);
    }

    /**
     * retourne le contexte de mainActivity
     * @return
     */
    public static MainActivity getContext() {
        return myCurrentActivity;
    }

    public void startSong() {
        if(!music.isPlaying()) {
            music.setLooping(true);
            music.start();
        }
    }

    public void pauseSong() {
        if(music.isPlaying()) {
            music.pause();
        }
    }
}