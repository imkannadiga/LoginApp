package com.example.alpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class signupActivity extends AppCompatActivity {

    Button signUp_button;
    TextView firstName, middleName, lastName, emailId, countryCode,phoneNumber, age, password, confirmPassword, signIn;
    Spinner gender;
    ProgressBar progressBar;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
            "(?=.*[@#$%^&+=])" +     // at least 1 special character
            "^.*[a-zA-Z0-9]+.*$" +   // at least one alpha numeric character
            "$");
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstName = (TextView) findViewById(R.id.first_name_editText);
        middleName = (TextView) findViewById(R.id.middle_name_editText);
        lastName = (TextView) findViewById(R.id.last_name_editText);
        emailId = (TextView) findViewById(R.id.emailID_editText);
        phoneNumber = (TextView) findViewById(R.id.mobile_editText);
        gender = (Spinner) findViewById(R.id.spinner);
        age = (TextView) findViewById(R.id.age_editText);
        password = (TextView) findViewById(R.id.password1_editText);
        confirmPassword = (TextView) findViewById(R.id.password2_editText);
        signIn = (TextView) findViewById(R.id.sign_in_insted);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        signUp_button = (Button) findViewById(R.id.sign_button);

        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading loading = new loading(signupActivity.this);
                loading.startLoadingDialog();
                if(validateInputs()){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(emailId.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isComplete()){
                                loading.stopLoading();
                                startActivity(new Intent(signupActivity.this,loginActivity.class));
                            }
                            else{
                                loading.stopLoading();
                                Toast.makeText(signupActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    loading.stopLoading();
                    //redirect to login activity and make toast saying registration successfull
                }
                else{
                    loading.stopLoading();
                    Toast.makeText(signupActivity.this, "please check inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signupActivity.this, loginActivity.class));
            }
        });
    }

    private boolean validateInputs(){
           return validateFirstName() && validateMiddleName() && validateLastname() && validateEmail()
                   && validatePhone() && validateAge() && validatePasswords();
    }

    private boolean validateFirstName(){
        String s = firstName.getText().toString();
        if(s.isEmpty()) {
            firstName.setError("First name cannot be empty");
            return false;
        }else if(!Character.isAlphabetic(s.charAt(0))){
            firstName.setError("Starting of name should be an alphabet");
            return false;
        }
        return true;
    }

    private boolean validateMiddleName(){
        String s = middleName.getText().toString();
        if(s.isEmpty()) return true;
        else if(!Character.isAlphabetic(s.charAt(0))){
            middleName.setError("Starting of name should be an alphabet");
            return false;
        }
        return true;
    }

    private boolean validateLastname(){
        String s = lastName.getText().toString();
        if(s.isEmpty()) {
            lastName.setError("First name cannot be empty");
            return false;
        }else if(!Character.isAlphabetic(s.charAt(0))){
            lastName.setError("Starting of name should be an alphabet");
            return false;
        }
        return true;
    }

    private boolean validateEmail(){
        String s = emailId.getText().toString();
        if(s.isEmpty()) {
            emailId.setError("Email cannot be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(s).matches()){
            emailId.setError("Invalid email format");
            return false;
        }
        return true;
    }

    private boolean validatePhone(){
        String s = phoneNumber.getText().toString();
        if(s.isEmpty()) return true;
        if(s.length()!=10){
            phoneNumber.setError("Invalid phone number");
            return false;
        }
        return true;
    }

    private boolean validateAge(){
        String s = age.getText().toString();
        if(s.isEmpty()) return true;
        else if(Integer.parseInt(s)<=0 || Integer.parseInt(s)>=100){
            age.setError("Invalid age");
            return false;
        }
        return true;
    }

    private boolean validatePasswords(){
        String s1 = password.getText().toString();
        String s2 = confirmPassword.getText().toString();

        if(s1.isEmpty()) {
            password.setError("password cannot be empty");
            return false;
        }
        if(s1.isEmpty()){
            confirmPassword.setError("confirm password cannot be empty");
            return false;
        }
        if(!s1.equals(s2)){
            confirmPassword.setError("Passwords donot match");
            return false;
        }
        if(!PASSWORD_PATTERN.matcher(s1).matches()){
            password.setError("Password should contain at least one letter, one number and one special character");
            return false;
        }
        return true;
    }
}