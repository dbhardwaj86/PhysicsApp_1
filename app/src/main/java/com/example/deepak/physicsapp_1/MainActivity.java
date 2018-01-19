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

    private static final String CHANNEL_URL = "http://www.youtube.com/user/JustinBieberVEVO";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_lectures:
                    Log.d("PApp", "Lectures pressed");
                    //Intent lectureIntent = new Intent(MainActivity.this, LectureActivity.class);
                    //startActivity(lectureIntent);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(CHANNEL_URL));
                    startActivity(intent);

                    return true;
                case R.id.navigation_previous_doubts:
                    Log.d("PApp", "Previous Doubts pressed");
                    Intent pDoubtIntent = new Intent(MainActivity.this, PreviousDoubtsActivity.class);
                    startActivity(pDoubtIntent);
                    return true;
                case R.id.navigation_one_on_one:
                    Log.d("PApp", "One-on-One pressed");
                    Intent oneOnOneIntent = new Intent(MainActivity.this, OneonOneActivity.class);
                    startActivity(oneOnOneIntent);
                    return true;
            }
            return false;
        }
    };

    private Button mAskDoubtButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAskDoubtButton = (Button) findViewById(R.id.button);
        mAskDoubtButton.setText(R.string.title_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mAskDoubtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PApp", "Ask Doubts pressed");
                Intent askDoubtsIntent = new Intent(MainActivity.this, AskDoubtsActivity.class);
                startActivity(askDoubtsIntent);
            }
        });

    }

}
