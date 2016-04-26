package edu.augustana.augiesnackers.studdybuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    private TextView welcome;
    private Firebase firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcome = (TextView)findViewById(R.id.textView);
        //an example of how the can link in  firebase, right now firebase just has my name on it
        firebase = new Firebase("https://studdy-buddy.firebaseio.com/name");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                welcome.setText("welcome to studdy buddy " +data);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
