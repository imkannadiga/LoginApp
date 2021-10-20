package com.example.testawsamplify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.result.AuthResetPasswordResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

public class forgotActivity extends AppCompatActivity {
    private EditText email;
    private Button submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        email = findViewById(R.id.EmailFP);
        submit = findViewById(R.id.FPbtn);


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




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.resetPassword(
                        email.getText().toString(),
                        result -> onSuccessful(v, result),
                        error -> onFailure(v, error)
                );

            }
        });

    }

    private void onSuccessful(View v, AuthResetPasswordResult result) {
        this.runOnUiThread(new Runnable() {
            public void run() {

                startActivity(new Intent(forgotActivity.this , confirmCodeActivity.class));

                }

        });
    }


    private void onFailure(View v, AuthException error){
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(v.getContext(), "failed", Toast.LENGTH_SHORT).show();
                Log.e("AuthQuickstart", error.toString());

            }

        });

    }
}
