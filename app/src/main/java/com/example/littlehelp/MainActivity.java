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

    private long backPressedTime;
    private Toast backToast;

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

    @Override
    public void onBackPressed() {

        SharedPreferences shared = getSharedPreferences("checkbox",MODE_PRIVATE);
        String val = shared.getString("remember","false");
        if(val.equals("true")){
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                finishAffinity();
                return;
            } else {
                backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
        else{
            super.onBackPressed();
        }
    }
}