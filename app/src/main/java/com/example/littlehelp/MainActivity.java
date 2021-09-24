package com.example.littlehelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logOut = (Button) findViewById(R.id.logoutbtn);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading loading = new loading(MainActivity.this);
                loading.startLoadingDialog();
                FirebaseAuth.getInstance().signOut();
                loading.stopLoading();
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                finish();
                startActivity(new Intent(MainActivity.this, loginActivity.class));







            }
        });
    }
}