package com.example.deepak.physicsapp_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AskDoubtsActivity extends AppCompatActivity {

    private ImageButton mAddDoubt;
    private ImageButton mLeftDoubt;
    private ImageButton mRightDoubt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_doubts);
        Log.d("PApp", "Ask Doubts activity started");

        mAddDoubt = (ImageButton)findViewById(R.id.add_button);
        mLeftDoubt = (ImageButton) findViewById(R.id.left_doubt);
        mRightDoubt = (ImageButton) findViewById(R.id.right_doubt);

        mAddDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PApp","Add Doubt clicked");
                Intent myIntent = new Intent(AskDoubtsActivity.this, AddDoubtActivity.class);
                startActivity(myIntent);
            }
        });
        mLeftDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PApp","Left Doubt clicked");
            }
        });
        mRightDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PApp","Right Doubt clicked");
            }
        });



    }
}
