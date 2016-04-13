package com.example.ashish.canvastutorial.signature;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ashish.canvastutorial.R;
import com.example.ashish.canvastutorial.utility.Base64ConvertorUtility;

import java.util.ArrayList;

public class SignatureActivity extends AppCompatActivity implements View.OnClickListener {
    private CanvasView customCanvas;
    ArrayList<Button> colorButtonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        setUpButton(R.id.red, Color.RED);
        setUpButton(R.id.blue, Color.BLUE);
        setUpButton(R.id.black, Color.BLACK);
        setUpButton(R.id.green, 0xFF006400); //dark green

        colorButtonList.add((Button) findViewById(R.id.red));
        colorButtonList.add((Button) findViewById(R.id.blue));
        colorButtonList.add((Button) findViewById(R.id.black));
        colorButtonList.add((Button) findViewById(R.id.green));

        showColorSelection(customCanvas.getColor());

        Base64ConvertorUtility.test();
    }

    public void setUpButton(int resId, int color){
        Button button = (Button) findViewById(resId);
        ColorFilter filter = new LightingColorFilter(color, color);
        button.getBackground().setColorFilter(filter);
        button.setTag(color); //tag is the color
        button.setOnClickListener(this);
        button.setTextColor(Color.WHITE);
    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

    private void showColorSelection(int color){
        //show selected button
        for(Button b : colorButtonList){
            if((int)b.getTag() == color){
                b.setText("o");
            }
            else{
                b.setText("");
            }
        }
    }

    public void onClick(View v){
        Object tag = v.getTag();
        if(tag != null && tag instanceof Integer){
            int color = (int) v.getTag();
            customCanvas.changeColor(color);
            showColorSelection(color);
        }
    }
}
