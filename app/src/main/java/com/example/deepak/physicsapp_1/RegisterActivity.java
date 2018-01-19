package com.example.deepak.physicsapp_1;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.activity_register);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        Button mRegisterButton = (Button) findViewById(R.id.btnRegister);

        name = (EditText) findViewById(R.id.reg_fullname);
        email = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_password);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        loginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name_content, email_content, password_content;
                name_content = name.getText().toString();
                email_content = email.getText().toString();
                password_content = password.getText().toString();

                Log.d("PApp", "Register clicked");
                mAuth.createUserWithEmailAndPassword(email_content, password_content)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.d("PApp", "Registration successful");
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_DB = mDatabaseReference.child(user_id);
                                    current_DB.child("Name").setValue(name_content);
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                                }else
                                {
                                    Toast.makeText(getApplicationContext(), "create failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("PApp", " "+e.getMessage());
                    }
                });



            }
        });


    }
}