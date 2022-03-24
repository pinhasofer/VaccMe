package com.example.vaccme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoPageActivity1 extends AppCompatActivity {
    Context context = InfoPageActivity1.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page1);


    }

    public void returnBtnClicked(View view){
        Helper.changeActivityAndFinish(context, MainScreenActivity.class);
    }
}