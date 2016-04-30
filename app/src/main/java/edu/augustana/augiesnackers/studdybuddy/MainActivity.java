package edu.augustana.augiesnackers.studdybuddy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter  adapter;
    private Firebase firebase;
    private EditText sendText;
    private ImageView sendbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        List<Data> data = fill_with_data();
        sendText = (EditText) findViewById(R.id.etStatus);
        sendbtn = (ImageView) findViewById(R.id.ivSend);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(data, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebase = new Firebase("https://studdy-buddy.firebaseio.com/name");

       //TODO Firebase Stuff https://www.firebase.com/docs/android/guide/
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
               // welcome.setText("welcome to studdy buddy " + data);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }


    //Create a list of Data objects
    public List<Data> fill_with_data() {
//dummy for testing replace with firebase data
        List<Data> data = new ArrayList<>();

        data.add(new Data("Batman vs Superman", "Diffusion Monte Carlo (DMC) is one of the variations of Monte Carlo processes, a class of numerical techniques that uses stochastic calculus to solve mathematical problems that are either too difficult or impossible to solve analytically. Although the specifics of Monte Carlo processes vary from application to application, the basic structure of such a process is as follows: A simulation involving large iterations of an algorithm manipulates a set of data in a random Following the destruction of Metropolis, Batman embarks on a personal vendetta against Superman ", R.drawable.ic_google));
        data.add(new Data("X-Men: Apocalypse", "X-Men: Apocalypse is an upcoming American superhero film based on the X-Men characters that appear in Marvel Comics ", R.drawable.ic_google));
        data.add(new Data("Captain America: Civil War", "A feud between Captain America and Iron Man leaves the Avengers in turmoil.  ", R.drawable.ic_google));
        data.add(new Data("Kung Fu Panda 3", "After reuniting with his long-lost father, Po  must train a village of pandas", R.drawable.ic_google));
        data.add(new Data("Warcraft", "Fleeing their dying home to colonize another, fearsome orc warriors invade the peaceful realm of Azeroth. ",R.drawable.ic_google));
        data.add(new Data("Alice in Wonderland", "Alice in Wonderland: Through the Looking Glass ", R.drawable.ic_google));


        return data;
    }


}
