package com.example.josh.battleshipsgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private GestureDetector mGestureDetector;
    private BattleshipGameBase.ShipData[] ships;
    private PlayerView playersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the gestureDetector to GestureListener
        mGestureDetector = new GestureDetector(this, new GestureListener());

        //Creates the ships data and sets gameData to the intent data
        ships = BattleshipGameBase.ShipData.CREATOR.newArray(5);
        Bundle gameData = getIntent().getExtras();

        //Unpacks the ship data from the intent
        for (int i = 0; i < 5; i++) {
            BattleshipGameBase.ShipData ship = gameData.getParcelable("ship" + String.valueOf(i));
            ships[i] = ship;
        }
        // Create a new game with the ships I have just unpacked from the intent
        PlayerLogic mGame = new PlayerLogic(10, 10, ships);

        // Set up a variable to refer to the correct view
        playersView = findViewById(R.id.PlayerView);

        //Replace the game object that the view is pointing to, with the new game object
        //you have created containing the ships from the intent
        playersView.setGame(mGame);
    }

    public void startNewGame(View view) {
        Intent intentStartNew = new Intent(this,StartActivity.class);
        startActivity(intentStartNew);
    }

    // onTouch() method gets called each time you perform any
    // touch event with screen
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // method onTouchEvent of GestureDetector class Analyzes the given motion event
        // and if applicable triggers the appropriate callbacks on the
        // GestureDetector.OnGestureListener supplied.
        // Returns true if the GestureDetector.OnGestureListener consumed
        // the event, else false.
        boolean eventConsumed = mGestureDetector.onTouchEvent(event);

            playersView.invalidate();
            return true;
    }
}

