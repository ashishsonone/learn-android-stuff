package com.example.ashish.canvastutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ashish.canvastutorial.canvas.CanvasActivity;
import com.example.ashish.canvastutorial.signature.SignatureActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.signature_button).setOnClickListener(this);
        this.findViewById(R.id.canvas_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent i = null;
        switch (v.getId()){
            case R.id.canvas_button:
                i = new Intent(this, CanvasActivity.class);
                startActivity(i);
                break;
            case R.id.signature_button:
                i = new Intent(this, SignatureActivity.class);
                startActivity(i);
                break;
        }
    }
}
