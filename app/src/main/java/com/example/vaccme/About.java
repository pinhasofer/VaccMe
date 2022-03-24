package com.example.vaccme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import android.widget.ImageView;


import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class About extends AppCompatActivity {

    ImageView about_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Element adsElement = new Element();
        adsElement.setTitle("VaccMe Application");
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.aboutim)
                .addItem(new Element().setTitle("Version 1.0"))
                .setDescription("VaccMe")
                .addItem(adsElement)
                .addEmail("vaccme2021@gmail.com")
                //.addItem(creatCopyright())
                .create();
        setContentView(aboutPage);
        //setContentView(R.layout.activity_about);
    }


   /*private Element creatCopyright() {
        Element copyright = new Element();
        String copyrightString = String.format("Copyright %d by EDMTDev", Calender.getInstance().get(CalendarContract.Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyrigt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(About.this, copyrightString, Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }*/
}