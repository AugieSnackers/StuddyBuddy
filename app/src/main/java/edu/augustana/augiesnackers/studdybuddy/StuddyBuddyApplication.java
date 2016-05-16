package edu.augustana.augiesnackers.studdybuddy;



import com.firebase.client.Firebase;
//https://github.com/firebase/FirebaseUI-Android

/**
 * Created by Nelly on 4/27/2016.
 * sets the application to use firebase rather than having to set the context in each class that uses firebase
 */
public class StuddyBuddyApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
