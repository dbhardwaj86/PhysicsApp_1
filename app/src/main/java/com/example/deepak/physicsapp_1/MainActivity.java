package com.example.deepak.physicsapp_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //final variables
    private String CHANNEL_URL;
    private final String TAG = "PApp MainActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_lectures:
                    Log.d(TAG, "Lectures pressed");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(CHANNEL_URL));
                    startActivity(intent);
                    return true;
                case R.id.navigation_previous_doubts:
                    Log.d(TAG, "Previous Doubts pressed");
                    Intent pDoubtIntent = new Intent(MainActivity.this, PreviousDoubtsActivity.class);
                    startActivity(pDoubtIntent);
                    return true;
                case R.id.navigation_one_on_one:
                    Log.d(TAG, "One-on-One pressed");
                    Intent oneOnOneIntent = new Intent(MainActivity.this, OneonOneActivity.class);
                    startActivity(oneOnOneIntent);
                    return true;
            }
            return false;
        }
    };

    private Button mAskDoubtButton;
    private BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CHANNEL_URL = this.getResources().getString(R.string.YTchannel);

        mAskDoubtButton = (Button) findViewById(R.id.button);
        mAskDoubtButton.setText(R.string.title_main);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mAskDoubtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Ask Doubts pressed");
                Intent askDoubtsIntent = new Intent(MainActivity.this, AskDoubtsActivity.class);
                startActivity(askDoubtsIntent);
            }
        });

    }

}
