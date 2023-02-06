package com.example.gkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button loginbutton;
    private FirebaseAuth authenticator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_acitvity);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbutton = findViewById(R.id.loginbutton);
        authenticator = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authenticator.getCurrentUser()==null){
            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = username.getText().toString();
                    String pass = password.getText().toString();
                    if (email.length()>0&&pass.length()>0){
                        loginUser(email,pass);
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"OOOps!! Empty credentials..",Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
        else{
            //            start home page intent
            Toast.makeText(LoginActivity.this,"you are already logged in",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,navigation_activity.class));
        }
    }

    public void loginUser(String name, String pass){
        authenticator.signInWithEmailAndPassword(name,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this,"Hurrrayy! Login was successful",Toast.LENGTH_SHORT).show();
                dataSaver(name);
                startActivity(new Intent(LoginActivity.this,navigation_activity.class));
//                start new intent here
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,"OOOps! Login failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void dataSaver(String s){
        SharedPreferences saver = getSharedPreferences("userdetails",MODE_PRIVATE);
        SharedPreferences.Editor editor = saver.edit();
        editor.putString("emailid",s);
        editor.apply();
    }
}