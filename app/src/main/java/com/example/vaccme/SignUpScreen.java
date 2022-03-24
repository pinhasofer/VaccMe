package com.example.vaccme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpScreen extends AppCompatActivity {
    //consts
    final String DB_URL = "https://vaccme-7908a-default-rtdb.europe-west1.firebasedatabase.app/";


    //declare vars
    TextInputLayout usernameField, passwordField, emailField;
    //database
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        //set the vars id's
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        emailField = findViewById(R.id.emailField);


    }

    public void loginBtnClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        finish();
    }

    public void signUpBtnClicked(View view) {
        //get the strings
        String email = emailField.getEditText().getText().toString();
        String username = usernameField.getEditText().getText().toString();
        String password = passwordField.getEditText().getText().toString();

        //check for errors

        if (checkInputEmptyFlag(email, username, password)) {
            if (Helper.isEmailLegit(email)) {
                if(password.length() >= 6){
                    //DO db stuff
                    Helper.writeNewUser(email, username, password, SignUpScreen.this, new Helper.FirebaseResultListener() {
                        @Override
                        public void onComplete(User user) {
                            Helper.connectedAppUser = user;
                            if(Helper.connectedAuthUser != null){
                                Helper.setIsAnon(false);
                                Log.d(Helper.TAG, "in Signup - connectedAuthUser - :"+ Helper.connectedAuthUser);
                                Log.d(Helper.TAG, "in Signup - connectedAppUser - :"+ Helper.connectedAppUser);
                                Toast.makeText(getApplicationContext(), "נרשם בהצלחה", Toast.LENGTH_SHORT).show();
                                //registered! move to menu
                                Helper.changeActivityAndFinish(SignUpScreen.this, MainScreenActivity.class);
                            }
                        }
                    });
//                    if(Helper.connectedAuthUser != null){
//                        Log.d(Helper.TAG, "in Signup - connectedAuthUser - :"+ Helper.connectedAuthUser);
//                        Log.d(Helper.TAG, "in Signup - connectedAppUser - :"+ Helper.connectedAppUser);
//                        Toast.makeText(getApplicationContext(), "נרשם בהצלחה", Toast.LENGTH_SHORT).show();
//                        //registered! move to menu
//                        Helper.changeActivityAndFinish(SignUpScreen.this, Dashboard.class);
                //}
                } else{
                    passwordField.setError("סיסמה צריכה ליהיות גדולה מ6 תווים");
                }
            }
        } else {
            emailField.setError("אנא הכנס דואר אלקטרוני.");
        }

    }

    public boolean checkInputEmptyFlag(@NonNull String email, String username, String password) {
        //TODO: enter the giant if tree into this
        if (!email.isEmpty()) {
            //dont send empty error
            Helper.disableSetError(emailField);
            if (!username.isEmpty()) {
                //dont send empty error
                Helper.disableSetError(usernameField);
                if (!password.isEmpty()) {
                    //dont send empty error
                    Helper.disableSetError(passwordField);

                    //end of empty checks.___________
                    return true;

                } else {
                    passwordField.setError("אנא הכנס סיסמה");
                }
            } else {
                usernameField.setError("אנא הכנס שם משתמש");
            }
    } else {
            emailField.setError("אנא הכנס דואר אלקטרוני.");
        }

        return false;
    }

}