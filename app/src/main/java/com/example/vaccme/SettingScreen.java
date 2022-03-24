package com.example.vaccme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.URL;

public class SettingScreen extends AppCompatActivity {

    final Context context = SettingScreen.this;
    Button settingChangePassOrMailBtn, adminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);
        settingChangePassOrMailBtn = findViewById(R.id.settingChangePassOrMailBtn);
        adminBtn = findViewById(R.id.adminBtn);

        //if admin show delete button
        if(Helper.connectedAppUser.isAdmin())
            adminBtn.setVisibility(View.VISIBLE);
        else
            adminBtn.setVisibility(View.GONE);

        //if anon dont show delete button
        if(Helper.isIsAnon())
            settingChangePassOrMailBtn.setVisibility(View.GONE);
        else
            settingChangePassOrMailBtn.setVisibility(View.VISIBLE);



        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                clicked_seker(  "https://docs.google.com/forms/d/e/1FAIpQLSevxkVGJuVl6R39csXTsj8DRIkGwbIoJ4T56xXAX0ZKbrDzZA/viewform?usp=sf_link");
            }


        });

    }
    public void clicked_seker(String url){
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
    }

    public void returntomainpage(View view) {
        Helper.changeActivityAndFinish(context, MainScreenActivity.class);
    }

    public void deleteUserBtnClicked(View view){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Helper.deleteUser(context);
                        Helper.changeActivityAndFinish(context, LoginScreen.class);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("אתה בטוח שאתה רוצה למחוק את המשתמש?").setPositiveButton("כן", dialogClickListener)
                .setNegativeButton("לא", dialogClickListener).show();
    }

    public void changePassAndMailClicked(View view){
        Helper.changeActivity(context, ChangePassAndEmailActivity.class);
    }

    public void aboutClicked(View view){
        Helper.changeActivity(context, About.class);
    }

    public void adminBoardClicked(View view){
        Helper.changeActivity(context, Dashboard.class);
    }

}