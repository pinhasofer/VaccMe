package com.example.vaccme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainScreenActivity extends AppCompatActivity {

    public static boolean testcase;
    public static int testcasee;
    private Context context = MainScreenActivity.this;

    //maor - add
    ImageButton mapBtn, dataBtn, calcBtn, settingsBtn, logoutBtn;
    TextView textView4; //the text for location button
    Button vacInfoBtn, ramzorBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //maor - add
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mapBtn = findViewById(R.id.serchbutton);
        dataBtn = findViewById(R.id.databutton);
        calcBtn = findViewById(R.id.calcubutton);
        settingsBtn = findViewById(R.id.setingButton);
        logoutBtn = findViewById(R.id.logoutButton);

        textView4 = findViewById(R.id.textView4);

        vacInfoBtn = findViewById(R.id.vacInfoBtn);
        ramzorBtn = findViewById(R.id.ramzorBtn);

        //if anonymous guest dont show the map
        if(Helper.isIsAnon()){
            mapBtn.setVisibility(View.GONE);
            textView4.setVisibility(View.GONE);
        }else{
            mapBtn.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.VISIBLE);
        }


    }

//next to map page
    public void mapage(View view) {
        Helper.changeActivity(context, Locations.class);
    }
//next to calculator page
    public void calculpage(View view) {
        Helper.changeActivity(context, Next_to_Calculator.class);
    }

//next to info page
    public void infopage(View view) {
        Helper.changeActivity(context, Next_Infomation_Page.class);
    }

//next to setting page
    public void settingpage(View view) {
        Helper.changeActivity(context, SettingScreen.class);
    }

//next to logoutpage
    public void logoutpage(View view) {
        //maor - change

        if(Helper.isIsAnon()){
            Helper.deleteUser(context);
        }
        else
            Helper.signOut(context);

        //after signing out move to login screen
        Helper.changeActivityAndFinish(context, LoginScreen.class);
    }

    public void infoBtnClicked(View view){
        Helper.changeActivity(context, InfoPageActivity1.class);
    }

    public void ramzorBtnClicked(View view){
        Helper.openUrl(context, Helper.RAMZOR_URL);
    }

    public void setVacInfoBtnClicked(View view){
        Helper.openUrl(context, Helper.VAC_INFO_URL);
    }

}