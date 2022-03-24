package com.example.vaccme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Next_to_Calculator extends AppCompatActivity {

    private Context context = Next_to_Calculator.this;

    Button click1;
    CheckBox chekpoint_1;
    CheckBox chekpoint_2;
    CheckBox chekpoint_3;
    CheckBox chekpoint_4;
    CheckBox chekpoint_5;
    CheckBox chekpoint_6;
    CheckBox chekpoint_7;
    CheckBox chekpoint_8;
    CheckBox chekpoint_9;
    CheckBox chekpoint_10;
    EditText hospit;
    EditText age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_to_calculator);

        chekpoint_1 = findViewById(R.id.check1);
        chekpoint_2 = (CheckBox) findViewById(R.id.check2);
        chekpoint_3 = (CheckBox) findViewById(R.id.check3);
        chekpoint_4 = (CheckBox) findViewById(R.id.check4);
        chekpoint_5 = (CheckBox) findViewById(R.id.check5);
        chekpoint_6 = (CheckBox) findViewById(R.id.check6);
        chekpoint_7 = (CheckBox) findViewById(R.id.check7);
        chekpoint_8 = (CheckBox) findViewById(R.id.check8);
        chekpoint_9 = (CheckBox) findViewById(R.id.check9);
        chekpoint_10 = (CheckBox) findViewById(R.id.check10);
        hospit = (EditText) findViewById(R.id.TextNumber);
        age = (EditText) findViewById(R.id.TextNumber2);
        click1 = (Button) findViewById(R.id.button11);
    }


    public int calcRiskGroup() {
        //arr to loop through
        int sum = 0;
        CheckBox[] arr = {chekpoint_1, chekpoint_2, chekpoint_3, chekpoint_4, chekpoint_5,
                chekpoint_6, chekpoint_7, chekpoint_8, chekpoint_9, chekpoint_10};

        //loop through the array
        for (CheckBox box : arr) {
            if (box.isChecked())
                // add to the RiskScore
                sum += 1;
        }

        //add  number of hospitiliztion
        int numOfH = Integer.parseInt(hospit.getText().toString());
        sum += numOfH;
        Log.d("myActivity","calcRiskGroup - sum = " + sum);

        return sum;
    }
    public int Level_Of_Risk(int sum){
        int score = 0;

        int agepoints= Integer.parseInt(age.getText().toString());
        if(((0<=agepoints && agepoints<=49) && (0<=sum && sum<=3))||((50<=agepoints && agepoints<=69) && (0<=sum && sum<=1)))
            score=1;
        else if(((0<=agepoints && agepoints<=49) && (4<=sum))||((50<=agepoints && agepoints<=69) && (2<=sum))||((70<=agepoints)&&(0<=sum && sum<=3)))
            score=2;
        else if((70<=agepoints) && (4<=sum))
            score=3;

        Log.d("myActivity","Level_Of_Risk - res = " + score);

        return score;
    }

    public void calc(View view) {
        String ageStr = age.getText().toString();
        String hospStr = hospit.getText().toString();

        int res;


        if (!ageStr.isEmpty() ) {
            if (ageStr.matches(".*\\d.*")) {
                age.setError(null);
                if (!hospStr.isEmpty()) {
                    if (hospStr.matches(".*\\d.*")) {
                        hospit.setError(null);
                        //passed all the test and move to next level

                        res = Level_Of_Risk(calcRiskGroup());
                        Intent intent = new Intent(context, calc_Results.class);
                        intent.putExtra("Res", res);
                        startActivity(intent);
                        finish();
                    }else
                        hospit.setError("עלייך להכניס מספר!");
                }else
                    hospit.setError("עלייך להכניס מספר!");
            }else
                age.setError("עלייך להכניס מספר!");
        }else
            age.setError("עלייך להכניס מספר!");




    }

    //back to main page
    public void returntomainpage(View view) {
        Helper.changeActivityAndFinish(context, MainScreenActivity.class);
    }

}