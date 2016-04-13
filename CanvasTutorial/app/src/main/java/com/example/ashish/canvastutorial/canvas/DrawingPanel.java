package com.example.ashish.canvastutorial.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ashish on 4/8/16.
 */
public class DrawingPanel extends SurfaceView implements SurfaceHolder.Callback{
    public int offset = 0;
    private PanelThread _thread;

    public static final String LOGTAG = "__canvas.DP";

    public DrawingPanel(Context context){
        super(context);
        init(context);
    }

    public DrawingPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawingPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        getHolder().addCallback(this);
    }

    @Override
    public void onDraw(Canvas canvas){
        //draw here
        Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(255, 0, 0));
        myPaint.setStrokeWidth(10);
        myPaint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(50 + offset, 50 + offset, offset + 100, offset + 100, myPaint);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        Log.d(LOGTAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        Log.d(LOGTAG, "surfaceDestroyed");

        try {
            _thread.setRunning(false);                //Tells thread to stop
            _thread.join();                           //Removes thread from mem.
        }
        catch (InterruptedException e) {
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        Log.d(LOGTAG, "surfaceCreated");
        setWillNotDraw(false); //Allows us to use invalidate() to call onDraw()

        _thread = new PanelThread(getHolder(), this); //Start the thread that
        _thread.setRunning(true);                     //will make calls to
        _thread.start();                              //onDraw()
    }
}
