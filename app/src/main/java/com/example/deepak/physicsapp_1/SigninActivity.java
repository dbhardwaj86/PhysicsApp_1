package com.example.deepak.physicsapp_1;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {
    private TextView mRegisterScreen;
    private Button mLoginButton;
    private FirebaseAuth mAuth;

    private EditText mEmail, mPassword;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(mAuth != null) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.signinscreen);

        mAuth = FirebaseAuth.getInstance();
        mRegisterScreen = (TextView) findViewById(R.id.link_to_register);
        mLoginButton = (Button) findViewById(R.id.btnLogin);

        mEmail = (EditText) findViewById(R.id.email_login);
        mPassword = (EditText) findViewById(R.id.password_login);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = mEmail.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email, password)

                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("PApp", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent askDoubtIntent = new Intent(SigninActivity.this, AskDoubtsActivity.class);
                                    updateUI(user);
                                    startActivity(askDoubtIntent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("PApp", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });


        // Listening to register new account link
        mRegisterScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });


    }

    public void updateUI(FirebaseUser user){
        if(!(user == null)) {
            FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    try {
                        if (snapshot.getValue() != null) {
                            try {
                                Log.d("PApp", "" + snapshot.getValue()); // your name values you will get here
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("TAG", " it's null.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    Log.e("onCancelled", " cancelled");
                }
            });

            }
    }
}