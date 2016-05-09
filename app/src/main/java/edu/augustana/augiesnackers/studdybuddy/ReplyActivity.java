package edu.augustana.augiesnackers.studdybuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReplyActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private Firebase firebase;
    private EditText sendText;
    private ImageView sendbtn;
    private Query mStatusRef;
    static Bitmap mBitmap ;

    FirebaseRecyclerAdapter<ResponsePost, View_Holder> firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        sendText = (EditText) findViewById(R.id.replyStatus);

        try {
            mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), LogInActivity.personPhoto);
        }catch (Exception e){

        }

        sendbtn = (ImageView) findViewById(R.id.ivSend);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResponsePost reply = new ResponsePost(LogInActivity.personName, LogInActivity.personId, sendText.getText().toString(), 5); //last int must be changed to topic post ID number
                firebase.push().setValue(reply, new Firebase.CompletionListener() {
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


        firebase = new Firebase("https://studdy-buddy.firebaseio.com/Reply");
        mStatusRef = firebase.limitToLast(10);


        firebaseAdapter = new FirebaseRecyclerAdapter<ResponsePost, View_Holder>(ResponsePost.class, R.layout.second_card_view, View_Holder.class, mStatusRef) {
            @Override
            public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_card_view, parent, false);
                View_Holder holder = new View_Holder(v);
                return holder;
            }
            @Override
            public void populateViewHolder(View_Holder holder, ResponsePost reply, int position) {
                holder.setName(reply.getName());
                holder.setDescription(reply.getDescription());
                // holder.setImage(mBitmap);
            }
        };

        recyclerView.setAdapter(firebaseAdapter);
        //TODO Firebase Stuff https://www.firebase.com/docs/android/guide/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAdapter.cleanup();
    }
}
