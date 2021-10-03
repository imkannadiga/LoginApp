package com.example.littlehelp;




import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;


// Splash screen activity
public class bootActivity extends AppCompatActivity {
    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(bootActivity.this,loginActivity.class);
                bootActivity.this.startActivity(mainIntent);
                bootActivity.this.finish();
            }
        }, 1000);

    }

}