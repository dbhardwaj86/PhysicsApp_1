package com.example.deepak.physicsapp_1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
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
import com.google.android.gms.tasks.OnFailureListener;
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
    private static int READ_REQ_CODE = 42;
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



       // File file = null;
//        try {
//            file = File.createTempFile("test2", "txt");
//        } catch( IOException e ) {
//
//        }
        Intent myIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        myIntent.addCategory(Intent.CATEGORY_OPENABLE);
        myIntent.setType("*/*");
        startActivityForResult(myIntent, READ_REQ_CODE);





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

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData){
        if (requestCode == READ_REQ_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().

            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.d("PApp", "Uri: " + uri.toString());


                Cursor cursor = this.getContentResolver()
                        .query(uri, null, null, null, null, null);

                assert cursor != null;
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                String name = cursor.getString(nameIndex);
                cursor.close();

                storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl("gs://physicsapp1-7f596.appspot.com").child(name);

                UploadTask uploadTask = storageReference.putFile(uri);
                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(!task.isSuccessful())
                            Log.d("PApp", "Can't upload");
                    }

                });


                //showImage(uri);
            }
        }
    }

}