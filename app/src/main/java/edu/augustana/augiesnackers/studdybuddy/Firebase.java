package edu.augustana.augiesnackers.studdybuddy;

import android.os.Bundle;

import com.firebase.client.Firebase;

/**
 * Created by Nelly on 4/24/2016.
 */
//sets firebase context
public class Firebase extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        com.firebase.client.Firebase.setAndroidContext(this);
    }


}
