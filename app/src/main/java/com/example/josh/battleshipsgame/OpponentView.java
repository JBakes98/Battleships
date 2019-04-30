package com.example.josh.battleshipsgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import java.util.Random;

public class OpponentView extends GridDraw {

    static BotLogic mBotGame = new BotLogic(columns, rows, ships);

    public OpponentView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initialise();
        rows = 10;
        columns = 10;
        mBotGame.createBotArray(columns, rows);
        mBotGame.placeBotShips();
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Paint paint;
        float diameterX, diameterY, chosenDiameter, separatorSize, canvasHeight;
        int noOfColSeparators, noOfRowSeparators;
        double SEPARATOR_RATIO = 0.2;
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
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row <  rows; row++) {

                //Checks if a ship is in this cell
                paint = emptyMark;

                if (mBotGame.botGrid[col][row] == 1){
                    paint = hitMark;
                }
                else if (mBotGame.botGrid[col][row] == 0) {
                    paint = missMark;
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
            int y = (int) ev.getY();
            int x = (int) ev.getX();
            int touchX = (int) (x/(canvasWidth/colCount));
            int touchY = (int) (y/(canvasWidth/rowCount));
            if (touchX < 10 && touchX >= 0 && touchY < 10 && touchY >= 0) {
                mBotGame.currentGuessAt(touchX, touchY);
                System.out.println(touchX + " " + touchY);
            }

            Random rand = new Random();
            int botX = rand.nextInt(10);
            int botY = rand.nextInt(10);

            System.out.println(botX + " " + botY);

            mPlayerGame.currentGuessAt(botY, botX);

            // Use invalidate() to cause view to be redrawn
            invalidate();
            return true;
        }
    }
}

