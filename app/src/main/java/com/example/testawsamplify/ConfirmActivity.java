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

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Button confirm = findViewById(R.id.button5);
        EditText code = findViewById(R.id.editTextCode);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.confirmSignUp(
                        MainActivity.emailv.getText().toString(),
                        code.getText().toString(),
                        result -> verificationSuccessful(v,result),
                        error -> verificationFailed(v,error)
                );
            }
        });
    }

    private void verificationSuccessful(View view, AuthSignUpResult result){
        this.runOnUiThread(new Runnable() {
            public void run() {
                Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                if(result.isSignUpComplete()) {
                    Toast.makeText(view.getContext(), "Sign up successful, please login to continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ConfirmActivity.this, LoginActivity.class));
                }
            }
        });
    }
    private void verificationFailed(View view, AuthException error){
        this.runOnUiThread(new Runnable() {
            public void run() {
                Log.e("AuthQuickstart", error.toString());
                Toast.makeText(view.getContext(), "Verification failed. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
