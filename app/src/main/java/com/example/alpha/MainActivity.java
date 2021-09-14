package com.example.alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sign_up(View view){
    startActivity(new Intent(this,signupActivity.class));

    }

    public  void log_in(View view){
        startActivity(new Intent(this, loginActivity.class));

    }


    public void share(View view){


    }


}