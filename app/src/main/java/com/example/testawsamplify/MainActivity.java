package com.example.testawsamplify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.MailTo;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.DataStoreConfiguration;
import com.amplifyframework.datastore.DataStoreException;
import com.amplifyframework.datastore.DataStoreItemChange;
import com.amplifyframework.datastore.generated.model.Userdata;

import org.w3c.dom.Text;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static EditText emailv , firstNamev , middleNamev , lastNamev ,mobile , age , passv;
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
        lastNamev=  findViewById(R.id.Lastname);
        mobile = findViewById(R.id.phone);
        age = findViewById(R.id.age_editText);
        passv = findViewById(R.id.Confirmpassword);
        gender = findViewById(R.id.spinner);
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

    private void addUserToDataStore(AuthSignUpResult result) throws DataStoreException , AmplifyException {
        if(result.isSignUpComplete()){
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

            Amplify.addPlugin( AWSDataStorePlugin.builder().dataStoreConfiguration(
                    DataStoreConfiguration.builder()
                            .syncExpression(Userdata.class, () ->  item.FIRST_NAME.eq(firstNamev.getText().toString()))
                            .syncExpression(Userdata.class, () ->  item.LAST_NAME.eq(lastNamev.getText().toString()))
                            .syncExpression(Userdata.class, () ->  item.MIDDLE_NAME.eq(middleNamev.getText().toString()))
                            .syncExpression(Userdata.class, () ->  item.EMAIL.eq(emailv.getText().toString()))
                            .syncExpression(Userdata.class, ()->   item.AGE.eq(Integer.parseInt(age.getText().toString())))
                            .syncExpression(Userdata.class, (() -> item.MOBILE_NO.eq(mobile.getText().toString())))
                            .syncExpression(Userdata.class,()->    item.GENDER.eq(gender.getSelectedItem().toString()))
                            .build())
                    .build());
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

    private void signUpFailure(AuthException error , View view) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(view.getContext(), "Sign up Failure", Toast.LENGTH_SHORT).show();
                Log.e("Amplify" ,"error" ,error  );
            }
        });
    }

    public void signInInsted(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}