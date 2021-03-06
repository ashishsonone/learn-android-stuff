package com.example.ashish.canvastutorial.canvas;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by ashish on 4/8/16.
 */
class PanelThread extends Thread {
    private SurfaceHolder _surfaceHolder;
    private DrawingPanel _panel;
    private boolean _run = false;

    public static final String LOGTAG = "__canvas.PT";

    public PanelThread(SurfaceHolder surfaceHolder, DrawingPanel panel) {
        _surfaceHolder = surfaceHolder;
        _panel = panel;
    }


    public void setRunning(boolean run) { //Allow us to stop the thread
        _run = run;
    }

    @Override
    public void run() {
        Log.d(LOGTAG, "run() entered");
        Canvas c;
        while (_run) {     //When setRunning(false) occurs, _run is
            c = null;      //set to false and loop ends, stopping thread
            try {
                c = _surfaceHolder.lockCanvas(null);
                synchronized (_surfaceHolder) {
                    //Insert methods to modify positions of items in onDraw()
                    updateItems();
                    _panel.postInvalidate();
                }
            } finally {
                if (c != null) {
                    _surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
        Log.d(LOGTAG, "run() exit");
    }

    private void updateItems(){
        _panel.offset += 10;
        if(_panel.offset > 150){
            _panel.offset = 0;
        }
    }
}