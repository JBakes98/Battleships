package com.example.josh.battleshipsgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class PlayerView extends GridDraw {

    static PlayerLogic mPlayerGame;

    public PlayerView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initialise();
        rows = 10;
        columns = 10;
        mGestureDetector = new GestureDetector(context, new PlayerView.MyGestureListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float diameterX, diameterY, chosenDiameter, separatorSize, canvasHeight;
        int noOfColSeparators, noOfRowSeparators;
        double SEPARATOR_RATIO = 0.2;
        Paint paint;
        rowCount = 10;
        colCount = 10;

        noOfColSeparators = colCount + 1;
        noOfRowSeparators = rowCount + 1;

        // Given the no of rows and cols, and the screen size, which choice of diameter
        // will best fit everything onto the screen
        diameterX = (float)(getWidth() / (colCount + noOfColSeparators * SEPARATOR_RATIO));
        diameterY = (float)(getHeight() / (rowCount + noOfRowSeparators * SEPARATOR_RATIO));

        // Choose the smallest of the two and save that in the variable chosenDiameter
        if (diameterX < diameterY)
            chosenDiameter = diameterX;
        else
            chosenDiameter = diameterY;
        separatorSize = (float) (chosenDiameter * SEPARATOR_RATIO);

        // Based on the chosenDiameter, calculate the size of the GameBoard
        canvasWidth = (noOfColSeparators * chosenDiameter * (float)SEPARATOR_RATIO)
                + colCount * chosenDiameter;

        canvasHeight = (noOfRowSeparators * chosenDiameter * (float)SEPARATOR_RATIO)
                + rowCount * chosenDiameter;

        // Draw the game board
        canvas.drawRect(0, 0, canvasWidth, canvasHeight, mGridPaint);
        float radius = chosenDiameter / 2;

        //Draw circles onto game board
        for (int col = 0; col < colCount; col++) {
            for (int row = 0; row < rowCount; row++) {

                //Checks if a ship is in this cell
                if (mPlayerGame.playerGrid[col][row] == -1) {
                    paint = emptyMark;
                } else if (mPlayerGame.playerGrid[col][row] == 1) {
                    paint = hitMark;
                } else if (mPlayerGame.playerGrid[col][row] == 0) {
                    paint = missMark;
                } else {
                    paint = shipMark;
                }

                //Calculate co-ordinates of circle
                float cx = separatorSize + (chosenDiameter + separatorSize) * col + radius;
                float cy = separatorSize + (chosenDiameter + separatorSize) * row + radius;
                canvas.drawCircle(cx, cy, radius, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean r = this.mGestureDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev) || r;
    }

    // This GestureListener class is enclosed within the GameView class
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener     {

        // You should always include onDown() and it should always return true.
        // Otherwise the GestureListener may ignore other events.
        public boolean onDown(MotionEvent ev) {
            return true;
        }

        public boolean onSingleTapUp(MotionEvent ev) {
            // Get location of touch
                // Use invalidate() to cause view to be redrawn
                invalidate();
                return true;
        }
    }

    public void setGame(PlayerLogic game) {
        mPlayerGame = game;
        mPlayerGame.createPlayerArray(10, 10);
        mPlayerGame.setPlayersShips();
        invalidate();
    }


}

