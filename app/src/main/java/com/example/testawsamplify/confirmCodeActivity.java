package com.example.testawsamplify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.result.AuthResetPasswordResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

public class confirmCodeActivity extends AppCompatActivity {
    EditText code , newPassword;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);
        btn = findViewById(R.id.button5);
        code = findViewById(R.id.editTextCode);
        newPassword = findViewById(R.id.newpassword);

        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify");
            e.printStackTrace();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.confirmResetPassword(
                        newPassword.getText().toString(),
                        code.getText().toString(),
                        () ->   Log.i("AuthQuickstart", "New password confirmed"),
                        error -> onFailure(error , v)// Log.e("AuthQuickstart", error.toString())
                );
            }
        });

    }

    private void onSucess(AuthResetPasswordResult result, View v ){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(confirmCodeActivity.this , LoginActivity.class));
            }
        });
    }

    private void onFailure(Object error, View view) {

        this.runOnUiThread(new Runnable() {
            public void run() {
                Log.e("AuthQuickstart", error.toString());
                Toast.makeText(view.getContext(), "Verification failed. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}