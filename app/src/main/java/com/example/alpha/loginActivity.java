package com.example.alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void login(View view){



    }

    public void forgotPassword(View view){
        startActivity(new Intent(this, forgotActivity.class));


    }

    public void NewUser(View view){
        startActivity(new Intent(this, signupActivity.class));

    }
}