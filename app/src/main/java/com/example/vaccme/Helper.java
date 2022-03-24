package com.example.vaccme;

import static android.provider.Settings.System.getString;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.UserDataReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Helper {
    //firebase stuff
    final static String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    final static FirebaseFirestore db = FirebaseFirestore.getInstance();
    final static FirebaseAuth auth = FirebaseAuth.getInstance();
    static FirebaseUser connectedAuthUser;
    static User connectedAppUser;
    private static boolean isAnon;

    final static String SITES_COL = "vacSites";
    final static String USERS_COL = "Users";
    final static String NOTIFICATION_CHANNEL =  "My Notification";

    //URLS
    final static String RAMZOR_URL = "https://play.google.com/store/apps/details?id=com.moh.alert.ramzor&hl=iw&gl=US";
    final static String VAC_INFO_URL = "https://datadashboard.health.gov.il/COVID-19/general?utm_source=go.gov.il&utm_medium=referral";


    //debug
    public  static final String TAG = "MyActivity";
    // permission stuff
    final private static int REQUEST_CODE_FOR_PERMISSION = 0x1001;
    final private static String[] PERMS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    // DB METHODS ////////////////////////////////////////////
    public static void writeNewUser(String email, String username, String password, Context context, FirebaseResultListener callback) {
        User user = new User(email, username);

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful()){
                Log.d(TAG, "writeNewUser - createUserWithEmailAndPassword: unsuccess");
                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                try{
                    throw(task.getException());
                } catch(FirebaseAuthUserCollisionException e) {
                    Toast.makeText(context, "משתמש כזה קיים כבר!", Toast.LENGTH_SHORT).show();
                }  catch(Exception e) {
                    Log.d(TAG, "were here");
                    Log.e(TAG, e.getMessage());
                }
            }
            else {
                connectedAuthUser = auth.getCurrentUser();
                db.collection(USERS_COL).document(connectedAuthUser.getUid()).set(user).addOnCompleteListener(task1 -> {
                    if (!task1.isSuccessful()) {
                        Log.d(TAG, "writeNewUser - set: unsuccess");
                        Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        callback.onComplete(user);
                    }

                });
            }
            });

    }

    public static boolean isIsAnon() {
        return isAnon;
    }

    public static void setIsAnon(boolean isAnon) {
        Helper.isAnon = isAnon;
    }

    /////////////////

    public static String getUserEmail(){
        return connectedAuthUser.getEmail();
    }

    interface FirebaseResultListener {
        void onComplete(User user);
    }

    public static void getAppUserObject(Context context, FirebaseResultListener callback){
        //this one has callback
        DocumentReference docRef = db.collection(USERS_COL).document(connectedAuthUser.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        User user = document.toObject(User.class);

                        callback.onComplete(user);
                        //connectedAppUser = document.toObject(User.class);
                    } else {
                        Toast.makeText(context, "Doc doesn't exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void getAppUserObject(Context context){
        //this one doesn't
        DocumentReference docRef = db.collection(USERS_COL).document(connectedAuthUser.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        connectedAppUser = document.toObject(User.class);
                    } else {
                        Toast.makeText(context, "Doc doesn't exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void signOut(Context context){
        if(Helper.connectedAuthUser != null){
            auth.signOut();
            connectedAppUser = null;
            Log.d(TAG,"signedOut - connectedAuthUser: " + connectedAuthUser);
            Log.d(TAG,"connectedAuthUser: " + connectedAppUser);
            changeActivityAndFinish(context, LoginScreen.class);
        }
    }

    public static void changeEmail(String newEmail, Context context){
        if(connectedAuthUser != null){
            connectedAuthUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d(TAG, "changeEmail() - updateEmail(): " + task.getException().getMessage());
                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        db.collection(USERS_COL).document(connectedAuthUser.getUid()).update("email", newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful())  {
                                    Log.d(TAG, "changeEmail() - collection.update(): " + task.getException().getMessage());
                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    getAppUserObject(context);
                                    Toast.makeText(context, "שונה בהצלחה", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
            });
        }

    }

    public static void changePassword(String password, Context context){
        if(connectedAuthUser != null){
            connectedAuthUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d(TAG, "changeEmail() - updateEmail(): " + task.getException().getMessage());
                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context, "שונה בהצלחה", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public static void deleteUser(Context context){
        if(connectedAppUser != null && connectedAuthUser != null){
            String uId = connectedAuthUser.getUid();
            connectedAuthUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Log.d(TAG, "account deleted");
                        auth.signOut();
                        Log.d(TAG, "account signed out");

                        //delete from firestore;
                        db.collection(USERS_COL).document(uId).delete();
                        Log.d(TAG, "account deleted in FireStore");
                        Toast.makeText(context, "משתמש נמחק בהצלחה", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "connectedAuthUser.delete() - failed");
                    }

                }
            });

        }
        else if(Helper.isIsAnon()){
            auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Log.d(TAG, "account deleted");
                        auth.signOut();
                        Log.d(TAG, "account signed out");
                    }
                }

            });
        }
    }


    //////////////////////////////////////////////////////////////////
    // AUX HELPER METHODS ////////////////////////////////////////////

    public static void disableSetError(TextInputLayout field) {
        field.setError(null);
        field.setErrorEnabled(false);
    }

    public static Boolean isEmailLegit(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static void changeActivity(Context context, Class<?> cls) {
        //Find out whats wrong!!
        //when entering context you need to enter ACTIVITY_NAME.this instead of getApplicationContext()
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void changeActivityAndFinish(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public static void changeActivityAndDelBackStack(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    public static boolean checkInternet(){
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    public static void openUrl(Context context, String url){
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }


    ///////////////////////////////////////////////////////////////////
    //NOTIFICATION FUNC////////////////////////////////////////////////

    public static void createNotif(Context context,String title, String body){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setSmallIcon(R.drawable.syringe);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, builder.build());   //id is arbitrary
    }
    public static void createNotifChannel(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    ///////////////////////////////////////////////////////////////////
    // testing
}
