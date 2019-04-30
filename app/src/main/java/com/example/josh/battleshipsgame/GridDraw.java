package com.example.josh.battleshipsgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;

public class GridDraw extends View {

    static int rows, columns;
    static BattleshipGameBase.ShipData[] ships = new BattleshipGameBase.ShipData[5];
    GestureDetector mGestureDetector;
    Paint mGridPaint, hitMark, shipMark, emptyMark, missMark, placementMark;
    float canvasWidth;
    int rowCount, colCount;

    static PlayerLogic mPlayerGame = new PlayerLogic(columns, rows, ships);
    static  BotLogic mBotGame = new BotLogic(columns, rows, ships);

    public GridDraw(Context context, AttributeSet attrs) {

        super(context, attrs);
        initialise();
        rows = 10;
        columns = 10;
        mBotGame.createBotArray(columns, rows);
        mBotGame.placeBotShips();
        mGestureDetector = new GestureDetector(context, new GridDraw.MyGestureListener());
    }

    public void initialise() {

        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint.setStyle(Paint.Style.FILL);
        mGridPaint.setColor(BLUE);
        hitMark = new Paint(Paint.ANTI_ALIAS_FLAG);
        hitMark.setStyle(Paint.Style.FILL);
        hitMark.setColor(RED);
        shipMark = new Paint(Paint.ANTI_ALIAS_FLAG);
        shipMark.setStyle(Paint.Style.FILL);
        shipMark.setColor(GRAY);
        emptyMark = new Paint(Paint.ANTI_ALIAS_FLAG);
        emptyMark.setStyle(Paint.Style.FILL);
        emptyMark.setColor(WHITE);
        missMark = new Paint(Paint.ANTI_ALIAS_FLAG);
        missMark.setStyle(Paint.Style.FILL);
        missMark.setColor(YELLOW);
        placementMark = new Paint(Paint.ANTI_ALIAS_FLAG);
        placementMark.setStyle(Paint.Style.FILL);
        placementMark.setColor(GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) { }

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
                mPlayerGame.currentGuessAt(touchX, touchY);
                System.out.println(touchX + " " + touchY);
            }

            Random rand = new Random();
            int botX = rand.nextInt(10);
            int botY = rand.nextInt(10);

            System.out.println(botX + " " + botY);
            mBotGame.currentGuessAt(botY, botX);

            if (mPlayerGame.checkForWinner() == 1) {
                System.out.println("Player wins");
            }
            else if (mPlayerGame.checkForWinner() == 2) {
                System.out.println("Bot wins");
            }
            // Use invalidate() to cause view to be redrawn
            invalidate();
            return false;
        }
    }
}

