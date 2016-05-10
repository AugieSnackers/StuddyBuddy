package edu.augustana.augiesnackers.studdybuddy;
import android.os.Bundle;
import com.firebase.client.Firebase;
//https://github.com/firebase/FirebaseUI-Android
/**
 * Created by Nelly on 4/27/2016.
 */
public class StuddyBuddyApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
