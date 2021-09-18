package com.example.alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class forgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }

    public void forgot_Password(View view){
        final loading  loadingDialog = new loading(forgotActivity.this);
        loadingDialog.startLoadingDialog();

    }
}