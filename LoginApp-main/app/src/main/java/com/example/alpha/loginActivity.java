package com.example.alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class loginActivity extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }



    public void login(View view){
        final loading  loadingDialog = new loading(loginActivity.this);
        loadingDialog.startLoadingDialog();




    }

    public void forgotPassword(View view){
        startActivity(new Intent(this, forgotActivity.class));


    }

    public void NewUser(View view){
        startActivity(new Intent(this, signupActivity.class));

    }
}