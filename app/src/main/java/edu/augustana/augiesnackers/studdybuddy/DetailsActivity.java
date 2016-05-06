package edu.augustana.augiesnackers.studdybuddy;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {
    private TextView welcome;
    private Firebase firebase;
    Typeface myTypeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        myTypeface = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");

       // welcome = (TextView)findViewById(R.id.textView);
        //an example of how the can link in  firebase, right now firebase just has my name on it

        firebase = new Firebase("https://studdy-buddy.firebaseio.com/name");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
}
