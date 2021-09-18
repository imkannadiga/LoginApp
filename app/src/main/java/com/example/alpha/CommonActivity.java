package com.example.alpha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        Button logOut = (Button) findViewById(R.id.logout);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading loading = new loading(CommonActivity.this);
                loading.startLoadingDialog();
                FirebaseAuth.getInstance().signOut();
                loading.stopLoading();
                startActivity(new Intent(CommonActivity.this, loginActivity.class));
            }
        });
    }
}
