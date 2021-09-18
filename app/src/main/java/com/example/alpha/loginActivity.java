package com.example.alpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button login_button;
    TextView forgotPassword,signup,share;
    EditText email,password;
    FirebaseAuth mAuth;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        login_button = (Button) findViewById(R.id.login_button);
        email = (EditText) findViewById(R.id.email_editText);
        password = (EditText) findViewById(R.id.password_edit_text);
        forgotPassword= (TextView) findViewById(R.id.forgot);
        signup = (TextView) findViewById(R.id.new_user);
        share = (TextView) findViewById(R.id.share);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading loading = new loading(loginActivity.this);
                loading.startLoadingDialog();
                boolean b1 = validateEmail();
                boolean b2 = validatePassword();
                if(b1&&b2){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loading.stopLoading();
                                Toast.makeText(loginActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(loginActivity.this,CommonActivity.class));
                            }else{
                                loading.stopLoading();
                                Toast.makeText(loginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    loading.stopLoading();
                    Toast.makeText(loginActivity.this, "Invalid inputs", Toast.LENGTH_SHORT).show();
                }
            }

        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this, forgotActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this, signupActivity.class));
            }
        });
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


    // press again to exit
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }


    public void share(View view){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "link to download app";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }



}