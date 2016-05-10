package edu.augustana.augiesnackers.studdybuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private Firebase firebase;
    private EditText sendText;
    private ImageView sendbtn;
    private Query mStatusRef;
    static Bitmap mBitmap ;

    FirebaseRecyclerAdapter<Status, View_Holder> firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sendText = (EditText) findViewById(R.id.etStatus);

        try {
            mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), LogInActivity.personPhoto);
        }catch (Exception e){

        }

        sendbtn = (ImageView) findViewById(R.id.ivSend);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),ReplyActivity.class);
//                startActivity(intent);
                Status status = new Status(LogInActivity.personName, LogInActivity.personId, sendText.getText().toString(), "#testing");
                firebase.push().setValue(status, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            Log.e("Error", firebaseError.toString());
                        }
                    }
                });
                sendText.setText("");
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        firebase = new Firebase("https://studdy-buddy.firebaseio.com/Status");
        mStatusRef = firebase.limitToLast(10);


        firebaseAdapter = new FirebaseRecyclerAdapter<Status, View_Holder>(Status.class, R.layout.card_view, View_Holder.class, mStatusRef) {
            @Override
            public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
                View_Holder holder = new View_Holder(v);
                return holder;
            }
            @Override
            public void populateViewHolder(View_Holder holder, Status status, int position) {
                holder.setName(status.getName());
                holder.setDescription(status.getDescription());
               // holder.setImage(mBitmap);
            }
        };

        recyclerView.setAdapter(firebaseAdapter);
        //TODO Firebase Stuff https://www.firebase.com/docs/android/guide/
//        Button replybtn = (Button)findViewById(R.id.reply_btn);
//        replybtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),ReplyActivity.class);
//
//                startActivity(intent);
//
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAdapter.cleanup();
    }
}
