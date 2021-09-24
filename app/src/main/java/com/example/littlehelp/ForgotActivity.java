package com.example.littlehelp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgotActivity extends AppCompatActivity {

    EditText resetmail;
    FirebaseAuth fAuth;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        resetmail = (EditText)findViewById(R.id.EmailFP);
        fAuth = FirebaseAuth.getInstance();
    }

    public void forgot_Password(View view){

        if(validateEmail()) {


            String mail = resetmail.getText().toString();
            fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(forgotActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(forgotActivity.this, loginActivity.class);
                    startActivity(myIntent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(forgotActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    private boolean validateEmail(){
        String s = resetmail.getText().toString();
        if(s.isEmpty()) {
            resetmail.setError("Email cannot be empty");
            return false;
        }
        return true;
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
}