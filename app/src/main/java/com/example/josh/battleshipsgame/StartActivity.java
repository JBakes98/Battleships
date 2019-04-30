package com.example.josh.battleshipsgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    private SetupView setupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setupView = findViewById(R.id.PlaceView);
    }

   public void launchGame(View view) {
        Intent intentPlayGame = new Intent(this, MainActivity.class);

        for (int i = 0; i < setupView.mPlayerGame.getShips().length; i++) {
            intentPlayGame.putExtra("ship" + String.valueOf(i), setupView.mPlayerGame.getShips()[i]);
        System.out.println("ship " + String.valueOf(i));
        }
       startActivity(intentPlayGame);
    }
}
