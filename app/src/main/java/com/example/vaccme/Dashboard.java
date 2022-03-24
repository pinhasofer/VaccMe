package com.example.vaccme;

import static com.example.vaccme.Helper.USERS_COL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.QuerySnapshot;

public class Dashboard extends AppCompatActivity {
    Context context= Dashboard.this;

    TextView userAmountTxt, internetTxt, NotificationTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userAmountTxt = findViewById(R.id.userAmountTxt);
        internetTxt = findViewById(R.id.internetTxt);
        NotificationTxt = findViewById(R.id.NotificationTxt);




    }

    public void signoutBtnClicked(View view){
        Helper.signOut(this);
    }

    public void checkInternetClicked(View view){
        boolean flag = Helper.checkInternet();

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("חיבור לשרתים: " + flag);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void changePassBtnClicked(View view){
        Helper.changePassword("aaaaaa",this);
    }

    public void deleteUserBtnClicked(View view){
        Helper.deleteUser(this);
    }

    public void mapsBtnClicked(View view){
        Helper.changeActivity(this, Locations.class);
    }

    public void getUserAmount(View view){
        Helper.db.collection(USERS_COL).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int count = task.getResult().size();
                    //create dialog
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("כמות משתמשים רשומים " + count);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {

                }
            }
        });
    }

    public void notifTxtClicked(View view){
        //this user story costs money so we made it locally but its easly implemented over cloud in firestore
        Helper.createNotifChannel(context);

        //create a layout for the dialog
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

    // Add a TextView here for the "Title" label, as noted in the comments
        final EditText titleBox = new EditText(context);
        titleBox.setHint("כותרת");
        layout.addView(titleBox); // Notice this is an add method

    // Add another TextView here for the "Description" label
        final EditText bodyBox = new EditText(context);
        bodyBox.setHint("גוף הפוש");
        layout.addView(bodyBox); // Another add method
        //////////////////////////////////////////////////

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("נוטיפיקציה")
                .setMessage("צור נוטיפיקציה חדשה")
                .setView(layout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String titleStr = titleBox.getText().toString();
                        String bodyStr = bodyBox.getText().toString();
                        if(!titleStr.isEmpty()){
                            if(!bodyStr.isEmpty()){
                                Helper.createNotif(context, titleStr, bodyStr);
                            }else{
                                Toast.makeText(getApplicationContext(), "צריך להכניס גוף", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "צריך להכניס כותרת", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }

}