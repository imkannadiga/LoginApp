package com.example.testawsamplify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import org.w3c.dom.Text;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static EditText emailv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify");
            e.printStackTrace();
        }
    }

    public void SignUp(View view) {
        emailv = findViewById(R.id.textView2);
        EditText passv = findViewById(R.id.editTextTextPassword);
        //String username = UUID.randomUUID().toString();
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), emailv.getText().toString())
                .build();
        Amplify.Auth.signUp(emailv.getText().toString(), passv.getText().toString(), options,
                result -> signUpSuccess(view),
                error -> signUpFailure(view)
        );
    }


    private void signUpSuccess(View view) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(view.getContext(), "Please enter verification code sent to your mail", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, ConfirmActivity.class));
            }
        });
    }
    private void signUpFailure(View view) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(view.getContext(), "Sign up Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signInInsted(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}