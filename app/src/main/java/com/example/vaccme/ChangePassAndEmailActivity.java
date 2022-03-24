package com.example.vaccme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassAndEmailActivity extends AppCompatActivity {
    Button passBtn, emailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_and_email);

        passBtn = findViewById(R.id.changePassBtn);
        emailBtn = findViewById(R.id.changeEmailBtn);
    }



    public void passBtnClicked(View view){
        EditText passEdtTxt= new EditText(this);
        passEdtTxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("שנה סיסמה")
                .setMessage("הכנס סיסמא חדשה")
                .setView(passEdtTxt)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String passStr = passEdtTxt.getText().toString();
                        if(!passStr.isEmpty()){
                            if(!(passStr.length() < 6)){
                                Helper.changePassword(passStr, getApplicationContext());
                            }else{
                                Toast.makeText(getApplicationContext(), "הסיסמה צריכה ליהיות גדולה מ6 תווים", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "צריך להכניס סיסמה", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void emailBtnClicked(View view){
        EditText emailEdtTxt= new EditText(this);
        emailEdtTxt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("שנה אימייל")
                .setMessage("הכנס אימייל חדש")
                .setView(emailEdtTxt)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String emailStr = emailEdtTxt.getText().toString();
                        if(!emailStr.isEmpty()){
                            if(!Helper.isEmailLegit(emailStr)){
                                Helper.changePassword(emailStr, getApplicationContext());
                            }else{
                                Toast.makeText(getApplicationContext(), "חייב ליהיות מייל", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "צריך להכניס אימייל", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

}