package com.example.josh.battleshipsgame;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    static String currentGestureDetected;
    // Overrides the callback methods of GestureDetector.SimpleOnGestureListener
    @Override
    public boolean onSingleTapUp(MotionEvent ev)
    {
        currentGestureDetected=ev.toString();
        return true;
    }
    //public boolean onScroll(MotionEvent e1, MotionEvent e2,
    //                        float distanceX, float distanceY)
    //{
    // currentGestureDetected=e1.toString()+ " "+e2.toString();
    //   return true;
    // }
}
