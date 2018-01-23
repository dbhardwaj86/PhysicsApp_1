package com.example.deepak.physicsapp_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.OpenableColumns;
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

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class AddDoubtActivity extends AppCompatActivity {

    private EditText mEditText;
    private DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    FirebaseStorage mStorage;
    private ImageView mAttachImage;
    //StorageReference storageRef = storage.getReferenceFromUrl("gs://physicsapp1-7f596.appspot.com").child("icons8-more-96.png");
    private FirebaseUser user;
    private static final String TAG = "AddDoubtActivity";
    private FirebaseStorage storage;
    private static int READ_REQ_CODE = 42;
    private boolean mFileToBeAttached;
    private String mAttachedFileName;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doubt);
        mStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = mStorage.getReferenceFromUrl("gs://physicsapp1-7f596.appspot.com").child("icons8-more-96.png");
        mImageView = (ImageView) findViewById(R.id.image);
        mAttachImage = (ImageView) findViewById(R.id.attach_button);
        mAttachImage.setImageResource(R.drawable.ic_action_name);
        MessageModel mMessageModel;

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
        //mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        //childRef = mDatabaseRef.child("recipes");
        MessageAdapter mMessageAdapter = new MessageAdapter(MessageModel.class, R.layout.messagetextlayout, MessageHolder.class, mDatabaseReference, getApplicationContext());
        //recipeRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mMessageAdapter);

        mAttachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PApp", "Attach button clicked");
//                Intent attachIntent = new Intent(AddDoubtActivity.this, AttachActivity.class);
//                startActivity(attachIntent);
                Intent myIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                myIntent.addCategory(Intent.CATEGORY_OPENABLE);
                myIntent.setType("*/*");
                startActivityForResult(myIntent, READ_REQ_CODE);
            }
        });


    }
    @Override
    protected void onStart(){
        Log.d("PApp", "Entered onStart for addDoubts");
        super.onStart();
//        FirebaseRecyclerAdapter <MessageModel, MessageViewHolder> FBRA = new FirebaseRecyclerAdapter<MessageModel, MessageViewHolder>(
//                MessageModel.class,
//                R.layout.messagetextlayout,
//                MessageViewHolder.class,
//                mDatabaseReference
//        ) {
//            @Override
//            protected void populateViewHolder(MessageViewHolder viewHolder, MessageModel model, int position) {
//                Log.d("PApp", "inside populate view");
//                viewHolder.setContent(model.getContent(), model.getFile());
//            }
//        };
//        mRecyclerView.setAdapter(FBRA);

    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("PApp", "onResume called");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData){
        if (requestCode == READ_REQ_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().

            mUri = null;
            if (resultData != null) {
                mUri = resultData.getData();
                Log.d("PApp", "Uri: " + mUri.toString());


                Cursor cursor = this.getContentResolver()
                        .query(mUri, null, null, null, null, null);

                assert cursor != null;
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                mAttachedFileName = cursor.getString(nameIndex);
                cursor.close();
                mFileToBeAttached = true;




                //showImage(uri);
            }
        }
    }
    public void sendButtonClicked (View view){
        final String messageValue = mEditText.getText().toString().trim();
        Log.d("PApp", "Entered sendButtonClicked");
        if(!TextUtils.isEmpty(messageValue)){
            final DatabaseReference newpost = mDatabaseReference.push();
            newpost.child("content").setValue(messageValue);
            if(mFileToBeAttached){
                mFileToBeAttached = false;
                storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl("gs://physicsapp1-7f596.appspot.com").child(mAttachedFileName);

                UploadTask uploadTask = storageReference.putFile(mUri);
                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(!task.isSuccessful())
                            Log.d("PApp", "Can't upload");
                    }

                });
            }
        }

    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public MessageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setContent (String s, File file){
            ImageView mImage = (ImageView) mView.findViewById(R.id.msgimage);
            TextView mContent = (TextView) mView.findViewById(R.id.messagetext);
            mContent.setText(s);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if(bitmap != null)
                mImage.setImageBitmap(bitmap);

        }
    }




}


