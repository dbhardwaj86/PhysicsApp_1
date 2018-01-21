package com.example.deepak.physicsapp_1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import java.io.File;
import java.io.IOException;

public class AttachActivity extends AppCompatActivity {

    private FirebaseUser user;
    private static final String TAG = "AttachActivity";
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach);

//        user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user == null){
//            //if user is not authenticated show authentication screen
//            Intent i = new Intent();
//            i.setClass(this, SigninActivity.class);
//            startActivity(i);
//        }

        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://physicsapp1-7f596.appspot.com").child("test2.txt");

        File file = null;
        try {
            file = File.createTempFile("test2", "txt");
        } catch( IOException e ) {

        }
        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(file));
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(!task.isSuccessful())
                    Log.d("PApp", "Can't upload");
            }
        });


//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_EXTERNAL_STORAGE},
//                    1);
//
//        }
    }

}