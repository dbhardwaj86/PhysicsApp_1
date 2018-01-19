package com.example.deepak.physicsapp_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDoubtActivity extends AppCompatActivity {

    private EditText mEditText;
    private DatabaseReference mDatabaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doubt);

        mEditText = (EditText)findViewById(R.id.editText);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("message");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("message2");

    }

    public void sendButtonClicked (View view){
        final String messageValue = mEditText.getText().toString().trim();
        if(!TextUtils.isEmpty(messageValue)){
            final DatabaseReference newpost = mDatabaseReference.push();
            newpost.child("content2").setValue(messageValue);
        }

    }
}
