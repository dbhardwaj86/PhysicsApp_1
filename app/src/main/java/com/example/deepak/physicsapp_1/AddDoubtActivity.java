package com.example.deepak.physicsapp_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class AddDoubtActivity extends AppCompatActivity {

    private EditText mEditText;
    private DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    FirebaseStorage mStorage;
    //StorageReference storageRef = storage.getReferenceFromUrl("gs://physicsapp1-7f596.appspot.com").child("icons8-more-96.png");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doubt);
        mStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = mStorage.getReferenceFromUrl("gs://physicsapp1-7f596.appspot.com").child("icons8-more-96.png");
        mImageView = (ImageView) findViewById(R.id.image);
        
        try {
            final File localFile = File.createTempFile("image", "jpg");
            Log.d("PApp", "Local file created");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("PApp", localFile.getAbsolutePath());

                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    if(bitmap != null)
                        mImageView.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}




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
