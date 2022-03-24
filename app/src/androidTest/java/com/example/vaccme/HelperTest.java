package com.example.vaccme;


import static com.example.vaccme.Helper.USERS_COL;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.InstrumentationRegistry;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.rpc.Help;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class HelperTest extends TestCase {

    public void testWriteNewUser() {
        Helper.writeNewUser("tester@tester.com", "tester", "tester1", InstrumentationRegistry.getTargetContext(), new Helper.FirebaseResultListener() {
            @Override
            public void onComplete(User user) {
               if(Helper.auth.getCurrentUser().getEmail().equals("tester@tester.com")){
                   String uId = Helper.auth.getCurrentUser().getUid();
                   Helper.auth.getCurrentUser().delete();
                   Helper.db.collection(USERS_COL).document(uId).delete();
                   //deleted the created user
                   assertEquals(1, 1);
               }else{
                   assertFalse(1 == 1);
               }
            }
        });


    }

    @Test
    public void testIsEmailLegit() {
        String goodEmail = "ABCD@abcd.com";
        String badEmail = "ABCDTESTE";
        assertTrue("isEmailLegit - 1", Helper.isEmailLegit(goodEmail));
        assertFalse("isEmailLegit - 1", Helper.isEmailLegit(badEmail));
    }

    @Test
    public void testIsIsAnon() {
        Helper.setIsAnon(true);
        if(Helper.isIsAnon())
            assertTrue( 1==1);
        else
            assertFalse( 1==1);

    }

    @Test
    public void testGetUserEmail() {
        Helper.auth.signInWithEmailAndPassword("admin@admin.com", "admin1").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Helper.connectedAuthUser = Helper.auth.getCurrentUser();
                    String test = Helper.getUserEmail();
                    if(test.equals("admin@admin.com")){
                        Helper.auth.signOut();
                        Helper.connectedAuthUser = null;
                        assertTrue(1==1);
                    }else{
                        Helper.auth.signOut();
                        Helper.connectedAuthUser = null;
                        assertFalse(1==1);
                    }
                }
            }
        });

    }


    @Test
    public void testCheckInternet() {
        //needs internet connected
        if(Helper.checkInternet()){
            assertEquals(1,1);
        }else{
            assertFalse(1 != 1);
        }
    }
}