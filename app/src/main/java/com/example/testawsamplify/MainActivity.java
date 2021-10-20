package com.example.testawsamplify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.DataStoreException;
import com.amplifyframework.datastore.generated.model.Userdata;


import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    static EditText emailv, firstNamev, middleNamev, pass2v, lastNamev, mobile, age, passv;
    static Spinner gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    public void SignUp(View view) {
        emailv = findViewById(R.id.emailid);
        firstNamev = findViewById(R.id.Firstname);
        middleNamev = findViewById(R.id.Midddlename);
        lastNamev = findViewById(R.id.Lastname);
        mobile = findViewById(R.id.phone);
        age = findViewById(R.id.age_editText);
        pass2v = findViewById(R.id.Password);
        passv = findViewById(R.id.Confirmpassword);
        gender = findViewById(R.id.spinner);
        //String username = UUID.randomUUID().toString();
        if (validateInputs()) {
            AuthSignUpOptions options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), emailv.getText().toString())
                    .build();
            Amplify.Auth.signUp(emailv.getText().toString(), passv.getText().toString(), options,
                    result -> signUpSuccess(result, view),
                    error -> signUpFailure(error, view)
            );
        }
    }


    private void signUpSuccess(AuthSignUpResult result, View view) {

        this.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    addUserToDataStore(result);
                } catch (AmplifyException e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), "Please enter verification code sent to your mail", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, ConfirmActivity.class));
            }
        });
    }

    private void addUserToDataStore(AuthSignUpResult result) throws DataStoreException, AmplifyException {
        if (result.isSignUpComplete()) {
            Userdata item = Userdata.builder()
                    .firstName(firstNamev.getText().toString())
                    .lastName(lastNamev.getText().toString())
                    .email(emailv.getText().toString())
                    .gender(gender.getSelectedItem().toString())
                    .age(Integer.parseInt(age.getText().toString()))
                    .middleName(middleNamev.getText().toString())
                    .mobileNo(mobile.getText().toString())
                    .build();
            Amplify.DataStore.save(
                    item,
                    success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
                    error -> Log.e("Amplify", "Could not save item to DataStore", error)
            );


//            Amplify.API.mutate(ModelMutation.create(item),
//
//                    response -> Log.i("MyAmplifyApp", "Todo with id: " + response.getData().getId()),
//                    error -> Log.e("MyAmplifyApp", "Create failed", error)
//            );

//            Amplify.addPlugin( AWSDataStorePlugin.builder().dataStoreConfiguration(
//                    DataStoreConfiguration.builder()
//                            .syncExpression(Userdata.class, () ->  item.FIRST_NAME.eq(firstNamev.getText().toString()))
//                            .syncExpression(Userdata.class, () ->  item.LAST_NAME.eq(lastNamev.getText().toString()))
//                            .syncExpression(Userdata.class, () ->  item.MIDDLE_NAME.eq(middleNamev.getText().toString()))
//                            .syncExpression(Userdata.class, () ->  item.EMAIL.eq(emailv.getText().toString()))
//                            .syncExpression(Userdata.class, ()->   item.AGE.eq(Integer.parseInt(age.getText().toString())))
//                            .syncExpression(Userdata.class, (() -> item.MOBILE_NO.eq(mobile.getText().toString())))
//                            .syncExpression(Userdata.class,()->    item.GENDER.eq(gender.getSelectedItem().toString()))
//                            .build())
//                    .build());


//            Amplify.addPlugin(new AWSDataStorePlugin.Builder(DataStoreConfiguration.builder()));
//            Amplify.addPlugin(new AWSDataStorePlugin.Builder()(DataStoreConfiguration.builder()
//                    .syncExpression(Userdata.class, () -> Userdata.LAST_NAME.eq(lastNamev.getText().toString()))
//                    .syncExpression(Userdata.class, () -> Userdata.FIRST_NAME.eq(firstNamev.getText().toString()))
//                    .syncExpression(Userdata.class, () -> Userdata.MIDDLE_NAME.eq(middleNamev.getText().toString()))
//                    .syncExpression(Userdata.class, () -> Userdata.EMAIL.eq(emailv.getText().toString()))
//                    .syncExpression(Userdata.class, ()->Userdata.AGE.eq(Integer.parseInt(age.getText().toString())))
//                    .syncExpression(Userdata.class, (() -> Userdata.MOBILE_NO.eq(mobile.getText().toString())))
//                    .syncExpression(Userdata.class,()-> Userdata.GENDER.eq(gender.getSelectedItem().toString()))
//                    .build()));

        }
    }

    private void signUpFailure(AuthException error, View view) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(view.getContext(), "Sign up Failure", Toast.LENGTH_SHORT).show();
                Log.e("Amplify", "error", error);
            }
        });
    }

    public void signInInsted(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean validateInputs() {
        return validateFirstName() && validateMiddleName() && validateLastname() && validateEmail()
                && validatePhone() && validateAge() && validatePasswords();
    }

    private boolean validateFirstName() {
        String s = firstNamev.getText().toString();
        if (s.isEmpty()) {
            firstNamev.setError("First name cannot be empty");
            return false;
        } else if (!Character.isAlphabetic(s.charAt(0))) {
            firstNamev.setError("Starting of name should be an alphabet");
            return false;
        }
        return true;
    }

    private boolean validateMiddleName() {
        String s = middleNamev.getText().toString();
        if (s.isEmpty()) return true;
        else if (!Character.isAlphabetic(s.charAt(0))) {
            middleNamev.setError("Starting of name should be an alphabet");
            return false;
        }
        return true;
    }

    private boolean validateLastname() {
        String s = lastNamev.getText().toString();
        if (s.isEmpty()) {
            lastNamev.setError("last name cannot be empty");
            return false;
        } else if (!Character.isAlphabetic(s.charAt(0))) {
            lastNamev.setError("Starting of name should be an alphabet");
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String s = emailv.getText().toString();
        if (s.isEmpty()) {
            emailv.setError("Email cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
            emailv.setError("Invalid email format");
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        String s = mobile.getText().toString();
        if (s.isEmpty()) return true;
        if (s.length() != 10) {
            mobile.setError("Invalid phone number");
            return false;
        }
        return true;
    }

    private boolean validateAge() {
        String s = age.getText().toString();
        if (s.isEmpty()) return true;
        else if (Integer.parseInt(s) <= 0 || Integer.parseInt(s) >= 100) {
            age.setError("Invalid age");
            return false;
        }
        return true;
    }

    private boolean validatePasswords() {
        String s1 = pass2v.getText().toString();
        String s2 = pass2v.getText().toString();

        if (s1.isEmpty()) {
            pass2v.setError("password cannot be empty");
            return false;
        }
        if (s2.isEmpty()) {
            pass2v.setError("confirm password cannot be empty");
            return false;
        }
        if (!s1.equals(s2)) {
            passv.setError("Passwords donot match");
            return false;
        }
        if (!PASSWORD_PATTERN.matcher(s1).matches()) {
            pass2v.setError("Password should contain at least one letter, one number and one special character");
            return false;
        }
        return true;
    }

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "^.*[a-zA-Z0-9]+.*$" +   // at least one alpha numeric character
                    "$");
}
