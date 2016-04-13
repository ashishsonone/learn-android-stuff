package com.example.ashish.canvastutorial.signature;

/**
 * Created by ashish on 4/9/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class CanvasView extends View {
    ArrayList<Path> pathList = new ArrayList<>();
    ArrayList<Integer> colorList = new ArrayList<>();

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    private Path mPath;
    private int mColor;

    Context context;
    private Paint mPaint;
    private float mX, mY;

    //time mode on
    public boolean MODE_TIME = true;
    public static final float TIME_TOLERANCE = 20; //ms
    private long lastTS = 0;

    private int pointCount = 0;
    private int cumPointCount = 0;

    private static final float TOLERANCE = 5;

    public static final String LOGTAG = "__signature.CV";

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        // we set a new Path
        mPath = new Path();
        mColor = Color.BLACK;

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);

        pathList.add(mPath);
        colorList.add(mColor);
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(LOGTAG, "onSizeChanged called");
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the mPath with the mPaint on the canvas when onDraw
        for(int i=0; i<pathList.size(); i++){
            Path p = pathList.get(i);
            int c = colorList.get(i);

            mPaint.setColor(c);
            canvas.drawPath(p, mPaint);
        }
    }

    public void clearCanvas() {
        colorList.clear();
        pathList.clear();

        mPath.reset();
        pathList.add(mPath);
        colorList.add(mColor);

        invalidate();
        cumPointCount = 0;
        pointCount = 0;
    }

    public void changeColor(int color){
        //screenShot();
        mPath = new Path();
        mColor = color;

        pathList.add(mPath);
        colorList.add(mColor);

        invalidate();
    }

    public int getColor(){
        return mColor;
    }

    private void screenShot(){
        mCanvas.drawColor(Color.WHITE);

        this.draw(mCanvas);
        saveToFile(mBitmap);
    }

    private void screenShotFail(){
        saveToFile(getDrawingCache());
    }

    private void saveToFile(Bitmap bitmap){
        if(bitmap == null){
            Log.d(LOGTAG, "saveToFile bitmap NULL");
            return;
        }
        try{
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File("/mnt/sdcard/ashish.jpg")));
            Log.d(LOGTAG, "saveToFile save success");
        }
        catch (IOException e){
            e.printStackTrace();
            Log.d(LOGTAG, "saveToFile IOException");
        }
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        pointCount = 0;
        lastTS = System.currentTimeMillis();

        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        boolean capture = (dx >= TOLERANCE || dy >= TOLERANCE);

        long t = System.currentTimeMillis();
        long elapsed = (t - lastTS);
        if(capture && MODE_TIME){
            capture = capture && (elapsed >= TIME_TOLERANCE);
            if(!capture) {
                Log.d(LOGTAG, "time guard SUCCESS");
            }
        }

        if (capture){
            //mPath.lineTo(x, y);
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;

            pointCount++;
        }
        else{
            Log.d(LOGTAG, "moveTouch ignore " + x + ", " + y + " elapsed=" + elapsed);
        }
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);

        if(pointCount == 0){
            mPath.addCircle(mX, mY, 5, Path.Direction.CW);
        }
        cumPointCount += pointCount;
        Log.d(LOGTAG, "pointCount=" + pointCount + ", cumPointCount=" + cumPointCount);
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(LOGTAG, "action down " + x + ", " + y);
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.d(LOGTAG, "action move " + x + ", " + y);
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.d(LOGTAG, "action up " + x + ", " + y);
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}