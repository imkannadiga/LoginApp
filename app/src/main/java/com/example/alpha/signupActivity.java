package com.example.alpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class signupActivity extends AppCompatActivity {

    Button signUp_button;
    TextView firstName, middleName, lastName, emailId,phoneNumber, age, password, confirmPassword, signIn;
    Spinner gender;
    CountryCodePicker countryCode;
    ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        countryCode = (CountryCodePicker) findViewById(R.id.ccp);

        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading loading = new loading(signupActivity.this);
                loading.startLoadingDialog();
                if(validateInputs()){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(emailId.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        // Create a new user with a first and last name
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("first_name", firstName.getText().toString());
                                        user.put("middle_name", middleName.getText().toString());
                                        user.put("last_name", lastName.getText().toString());
                                        user.put("email_id", emailId.getText().toString());
                                        user.put("Country_Code",countryCode.toString());
                                        user.put("gender",gender.toString());
                                        user.put("age",age.getText().toString());
                                        user.put("password",password.getText().toString());

                                        // Add a new document with a generated ID
                                        db.collection("users")
                                                .add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        loading.stopLoading();
                                                        Toast.makeText(signupActivity.this, "User registration Successful", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(signupActivity.this,loginActivity.class));
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(signupActivity.this, "User registration failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    else{
                                        loading.stopLoading();
                                        Toast.makeText(signupActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                                    }
                                }
                    });
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