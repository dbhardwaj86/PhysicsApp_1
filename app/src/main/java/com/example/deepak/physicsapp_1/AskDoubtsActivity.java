package com.example.deepak.physicsapp_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class AskDoubtsActivity extends AppCompatActivity {

    private final String TAG = "PApp AskDoubtsActivity";

    private ImageButton mAddDoubt;
    private ImageButton mLeftDoubt;
    private ImageButton mRightDoubt;
    private ImageButton mLogOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
       // Log.d("Papp2", mAuth.getCurrentUser().toString());

        if(mAuth.getCurrentUser() == null) {
            Intent myIntent = new Intent(getApplicationContext(), SigninActivity.class);
            startActivity(myIntent);
            finish();
        }

        setContentView(R.layout.activity_ask_doubts);
        Log.d(TAG, "Ask Doubts activity started");

        mAddDoubt = (ImageButton)findViewById(R.id.add_button);
        mLeftDoubt = (ImageButton) findViewById(R.id.left_doubt);
        mRightDoubt = (ImageButton) findViewById(R.id.right_doubt);
        mLogOutButton = (ImageButton) findViewById(R.id.logOutButton);

        mAddDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Add Doubt clicked");
                Intent myIntent = new Intent(AskDoubtsActivity.this, AddDoubtActivity.class);
                startActivity(myIntent);
            }
        });
        mLeftDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Left Doubt clicked");
            }
        });
        mRightDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Right Doubt clicked");
            }
        });
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent logOutIntent;
                logOutIntent = new Intent(AskDoubtsActivity.this, MainActivity.class);
                startActivity(logOutIntent);
                finish();
            }
        });



    }
}
