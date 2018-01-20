package com.example.deepak.physicsapp_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDoubtActivity extends AppCompatActivity {

    private EditText mEditText;
    private DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doubt);

        mEditText = (EditText)findViewById(R.id.editText);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("message");

        mRecyclerView = (RecyclerView) findViewById(R.id.messageRec);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);



    }
    @Override
    protected void onStart(){
        Log.d("PApp", "Entered onStart for addDoubts");
        super.onStart();
        FirebaseRecyclerAdapter <MessageModel, MessageViewHolder> FBRA = new FirebaseRecyclerAdapter<MessageModel, MessageViewHolder>(
                MessageModel.class,
                R.layout.messagetextlayout,
                MessageViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, MessageModel model, int position) {
                Log.d("PApp", "inside populate view");
                viewHolder.setContent(model.getContent());
            }
        };
        mRecyclerView.setAdapter(FBRA);

    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("PApp", "onResume called");
    }



    public void sendButtonClicked (View view){
        final String messageValue = mEditText.getText().toString().trim();
        Log.d("PApp", "Entered sendButtonClicked");
        if(!TextUtils.isEmpty(messageValue)){
            final DatabaseReference newpost = mDatabaseReference.push();
            newpost.child("content").setValue(messageValue);
        }

    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public MessageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setContent (String s){
            TextView mContent = (TextView) mView.findViewById(R.id.messagetext);
            mContent.setText(s);
        }
    }
}
