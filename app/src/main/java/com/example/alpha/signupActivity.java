package com.example.alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class signupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

    }


    public void signin(View view){
        final loading  loadingDialog = new loading(signupActivity.this);
        loadingDialog.startLoadingDialog();



    }


    public void log_In(View view){
        startActivity(new Intent(this, loginActivity.class));



    }
}