package com.example.ashish.fragmentlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TokenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        final String name = getIntent().getExtras().getString("name");

        final EditText tokenInputET = (EditText) findViewById(R.id.tokenInput);
        Button tokenSubmitButton = (Button) findViewById(R.id.tokenSubmit);

        tokenSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("token", name + "-" + tokenInputET.getText());
                setResult(RESULT_OK, data);
                //---close the activity---
                finish();
            }
        });
    }
}
