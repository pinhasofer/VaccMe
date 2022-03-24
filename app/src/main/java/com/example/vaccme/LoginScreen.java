package com.example.vaccme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.rpc.Help;

import java.util.concurrent.ExecutionException;

//test Aidfunc implements
public class LoginScreen extends AppCompatActivity {


    //declare vars
    private static final int MIN_PASS_LENGTH = 6;
    //final String DB_URL = "https://vaccme-7908a-default-rtdb.europe-west1.firebasedatabase.app/";
    Button signUpBtn, loginBtn, anonBtn;
    TextInputLayout emailField, passwordField;

    final Context context = LoginScreen.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        //set the id's
        signUpBtn = findViewById(R.id.signUpBtn);
        loginBtn = findViewById(R.id.loginBtn);
        anonBtn = findViewById(R.id.anonBtn);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);

        //listener for the login button

        //talked to shahar i need to reimplement the database using auth
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailField.getEditText().getText().toString();
                String password = passwordField.getEditText().getText().toString();

                if (!email.isEmpty()) {
                    //dont send empty error
                    Helper.disableSetError(emailField);

                    if (!password.isEmpty()) {
                        if(password.length() >= MIN_PASS_LENGTH){


                            Helper.disableSetError(passwordField);

                            //passed empty tests
                            //need for some goddamned reason to copy the string again...
                            final String emailDB = emailField.getEditText().getText().toString();
                            final String passwordDB = passwordField.getEditText().getText().toString();

                            //testing the login script
                            Helper.auth.signInWithEmailAndPassword(emailDB, passwordDB).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        Helper.connectedAuthUser = Helper.auth.getCurrentUser();
                                        Log.d(Helper.TAG,"DocumentSnapshot data: " +  Helper.connectedAuthUser.getUid());

                                        Helper.getAppUserObject(getApplicationContext(), new Helper.FirebaseResultListener() {
                                            @Override
                                            public void onComplete(User user) {
                                                Helper.connectedAppUser = user;
                                                Helper.setIsAnon(false);
                                                Log.d(Helper.TAG,"connectedAppUser: " +  Helper.connectedAppUser);
                                                if (Helper.connectedAuthUser != null && Helper.connectedAppUser != null){
                                                    Toast.makeText(getApplicationContext(), Helper.connectedAppUser.getUsername() + " התחבר בהצלחה ", Toast.LENGTH_SHORT).show();

                                                    //move to the mainScreen activity
                                                    Helper.changeActivityAndDelBackStack(LoginScreen.this,MainScreenActivity.class);
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        Log.d(Helper.TAG, "signInWithEmailAndPassword() error:" + task.getException().getMessage());
                                        try {
                                            throw task.getException();
                                        } catch(FirebaseAuthWeakPasswordException e) {
                                            passwordField.setError("סיסמה קטנה מ6 תווים");
                                            passwordField.requestFocus();
                                        } catch(FirebaseAuthInvalidCredentialsException e) {
                                            passwordField.setError("סיסמה לא נכונה");
                                            passwordField.requestFocus();
                                        }  catch(Exception e) {
                                            Toast.makeText(getApplicationContext(), "בעיית התחברות כנראה משתמש לא קיים", Toast.LENGTH_SHORT).show();
                                            Log.e(Helper.TAG, e.getMessage());
                                        }
                                    }
                                }
                            });

                        }else{
                            passwordField.setError("סיסמה צריכה ליהיות מעל 6 תווים");
                        }

                    } else {
                        passwordField.setError("אנא הכנס סיסמה");
                    }
                } else {
                    emailField.setError("אנא הכנס אימייל");
                }
            }
        });


        //listener for the signup button
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when clicked move to the registration activity.
                Helper.changeActivity(context, SignUpScreen.class);
            }
        });

        //listener for the anonymus login
        anonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //signIn anonymously
                Helper.auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //login anonymously
                            Helper.connectedAuthUser = Helper.auth.getCurrentUser();
                            Helper.connectedAppUser = new User("no mail", "anon");
                            Helper.setIsAnon(true);
                            Toast.makeText(getApplicationContext(), "משתמש אנונימי", Toast.LENGTH_SHORT).show();
                            //move to the dashboard;
                            Helper.changeActivityAndFinish(context, MainScreenActivity.class);
                        }
                        else{
                            Toast.makeText(context, "בעיית התחברות אנונימית", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}