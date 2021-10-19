package com.example.testawsamplify;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.MailTo;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Userdata;

import org.w3c.dom.Text;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static EditText emailv , firstNamev , middleNamev , lastNamev ,mobile , age , passv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify");
            e.printStackTrace();
        }
    }

    public void SignUp(View view) {
        emailv = findViewById(R.id.emailid);
         firstNamev = findViewById(R.id.Firstname);
         middleNamev = findViewById(R.id.Midddlename);
         lastNamev=  findViewById(R.id.Lastname);
        mobile = findViewById(R.id.phone);
        age = findViewById(R.id.age_editText);
        passv = findViewById(R.id.Confirmpassword);
        //String username = UUID.randomUUID().toString();
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), emailv.getText().toString())
                .build();
        Amplify.Auth.signUp(emailv.getText().toString(), passv.getText().toString(), options,
                result -> signUpSuccess(result , view),
                error -> signUpFailure(error, view)
        );
    }


    private void signUpSuccess(AuthSignUpResult result, View view) {

        this.runOnUiThread(new Runnable() {
            public void run() {

               if(result.isSignUpComplete()){
                    Userdata item = Userdata.builder()
                            .FirstName(firstNamev.getText().toString())
                            .MiddleName(middleNamev.getText().toString())
                            .LastName(lastNamev.getText().toString())
                            .MobileNo(lastNamev.getText())
                            .Email(emailv.getText().toString())
                            .Gender(" ")
                            .Age(emailv.getText().toString())
                            .build();
                    Amplify.DataStore.save(
                            item,
                            success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
                            error -> Log.e("Amplify", "Could not save item to DataStore", error)
                    );
                   );
                }


                Toast.makeText(view.getContext(), "Please enter verification code sent to your mail", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, ConfirmActivity.class));
            }
        });
    }
    private void signUpFailure(AuthException error , View view) {
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