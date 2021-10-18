package com.example.testawsamplify;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.core.Amplify;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = (Button) findViewById(R.id.button3);

        EditText emailv = findViewById(R.id.editTextTextEmailAddress);
        EditText passv = findViewById(R.id.editTextTextPassword2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signIn(
                        emailv.getText().toString(),
                        passv.getText().toString(),
                        result -> signInSuccessfull(result,v),
                        error -> signInFailure(error,v)
                );
            }
        });
    }
    private void signInSuccessfull(AuthSignInResult result, View view){
        this.runOnUiThread(new Runnable() {
            public void run() {
                Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                if(result.isSignInComplete())
                    Toast.makeText(view.getContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInFailure(AuthException error, View view){
        this.runOnUiThread(new Runnable() {
            public void run() {
                Log.e("AuthQuickstart", error.toString());
                Toast.makeText(view.getContext(), "Sign in Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
