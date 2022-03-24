package com.example.vaccme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.rpc.Help;


public class calc_Results extends AppCompatActivity {
    Context context = calc_Results.this;

    TextView text;

    String text_1="הינך נמצא בסיכון רגיל\n" +
            "כל מי שבסיכון רגיל צריך להקפיד על ההנחיות של שגרת הקורונה: שמירה על מרחק של שני מטרים, שימוש במסכה, שמירה על היגיינת ידיים ואי־יציאה מהבית בכל מקרה של התגלות תסמין. אם מישהו מבני המשפחה שייך לקבוצת סיכון - יש להימנע ככל הניתן מנטילת סיכונים.";
    String text_2="\u200Bהינך נמצא בסיכון גבוה\n" +
            "כל מי שבסיכון גבוה צריך לעשות מאמץ שלא להיחשף ולא להידבק. על אנשים בקבוצת הסיכון הזאת להפעיל שיקול דעת ולנהל נכון את הסיכונים. משמעות הדבר היא להימנע מפעילות בסיכון גבוה (כל התקהלות או מגע הדוק עם אנשים מחוץ למשפחה הגרעינית).";
    String text_3="\u200Bהינך נמצא בסיכון מרבי\n" +
            "בתקופה של גל תחלואה משמעותי בקורונה, כל מי שבסיכון מרבי צריך, עד כמה שניתן, להישאר בבית. כל יציאה למרחב הציבורי וכל מפגש עם אדם שאינו מהמשפחה הגרעינית הוא סיכון מיותר שלא כדאי לקחת בעת הזאת.";
    int res;
    //get the int from other screen
   // int res = getIntent().getIntExtra("Res",-1);    //-1 default


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_results);
        text=(TextView) findViewById(R.id.textView11);
        res =  getIntent().getIntExtra("Res", -1);
        textOfCalc();

    }


    public void textOfCalc(){
        if(res==1){
            text.setText(text_1);
        }
        else if (res==2){
            text.setText(text_2);
        }
        else if (res==2){
            text.setText(text_3);
        }
    }


    public void returntomainpage(View view) {
        Helper.changeActivityAndFinish(context,  MainScreenActivity.class);
        finish();
    }
}