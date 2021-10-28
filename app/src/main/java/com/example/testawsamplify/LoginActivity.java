package com.example.testawsamplify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

public class LoginActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button login_button;
    TextView forgotPassword,signup,share;
    EditText email,password;
    CheckBox remember;

    private long backPressedTime;
    private Toast backToast;
    loading loading = new loading(LoginActivity.this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_button = (Button) findViewById(R.id.login_button);
        email = (EditText) findViewById(R.id.email_editText);
        password = (EditText) findViewById(R.id.password_edit_text);
        forgotPassword = (TextView) findViewById(R.id.forgot);
        signup = (TextView) findViewById(R.id.new_user);
        share = (TextView) findViewById(R.id.share);
        remember = (CheckBox) findViewById(R.id.remember);
        TextView account = findViewById(R.id.new_user);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")) {
            Intent intent = new Intent(LoginActivity.this, commonActivity.class);
            startActivity(intent);
        }
        /*
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify");
            e.printStackTrace();
        }

         */

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , MainActivity.class));
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                boolean b1 = validateEmail();
                boolean b2 = validatePassword();
                if (b1 && b2) {
                    if (buttonView.isChecked()) {
                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember", "true");
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "checked", Toast.LENGTH_SHORT).show();

                    } else if (!buttonView.isChecked()) {

                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember", "false");
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();


                    }

                }
            }

        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, forgotActivity.class));
            }
        });



        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkEmail = validateEmail();
                boolean checkPass = validatePassword();
                if(checkEmail && checkPass){
                    loading.startLoadingDialog();
                    Amplify.Auth.signIn(
                            email.getText().toString(),
                            password.getText().toString(),
                            result -> signInSuccessfull(result,v),
                            error -> signInFailure(error,v)
                    );
                }
            }
        });
    }
    private void signInSuccessfull(AuthSignInResult result, View view){
        this.runOnUiThread(new Runnable() {
            public void run() {
                Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                loading.stopLoading();
                if(result.isSignInComplete())
                    Toast.makeText(view.getContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this , commonActivity.class ));
            }
        });
    }

    private void signInFailure(AuthException error, View view){

        this.runOnUiThread(new Runnable() {
            public void run() {
                loading.stopLoading();
                Log.e("AuthQuickstart", error.toString());
                Toast.makeText(view.getContext(), "Sign in Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
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
    private boolean validateEmail(){
        String s = email.getText().toString().trim();
        if(s.length()==0){
            email.setError("Email cannot be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(s).matches()){
            email.setError("Invalid email format");
            return false;
        }else{
            return true;
        }
    }

    private boolean validatePassword() {
        String s = password.getText().toString().trim();
        if(s.isEmpty()){
            password.setError("Password cannot be empty");
            return false;
        }
        return true;
    }

    public void share(View view){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://drive.google.com/drive/folders/1sytCy_N5vAMC_TVxa3A8elw-gw1Dll-k?usp=sharing";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

}
