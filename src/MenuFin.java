package com.example.projetandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuFin extends Activity {

    private Button btnRetry;
    private Button btnMenu;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_fin);

        gameView=new GameView(this);
        btnRetry = (Button) findViewById(R.id.Retry);
        btnMenu = (Button) findViewById(R.id.Menu);

        String scoreFin = getIntent().getStringExtra("score");

        TextView scoreMenu = (TextView) findViewById(R.id.score);
        scoreMenu.setText(scoreFin);

        /* On appuie sur le bouton RETRY */
        btnRetry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(gameView);
            }
        });

        /* On appuie sur le bouton MENU  */
        btnMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchMenu();
            }
        });
    }

    /**
     * Retour au menu principal
     */
    private void switchMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
