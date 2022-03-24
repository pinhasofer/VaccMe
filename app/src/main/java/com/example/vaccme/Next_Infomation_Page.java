package com.example.vaccme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Next_Infomation_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_infomation_page);
    }
    //back to main page
    public void returntomainpage(View view) {
        Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
        startActivity(intent);
    }
}