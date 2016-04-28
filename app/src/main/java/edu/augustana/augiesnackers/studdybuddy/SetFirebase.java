package edu.augustana.augiesnackers.studdybuddy;
import android.os.Bundle;
import com.firebase.client.Firebase;
//https://github.com/firebase/FirebaseUI-Android
/**
 * Created by Nelly on 4/27/2016.
 */
public class SetFirebase extends android.app.Application {
    private void OnCreate(){
      super.onCreate();
       Firebase.setAndroidContext(this);
  }
}
