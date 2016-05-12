package edu.augustana.augiesnackers.studdybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class ReplyActivity extends AppCompatActivity {
    private Firebase statusRef;
    private Firebase mReplyRef;
    private Query mReplyQuery;
    private Query mStatusRefQuery;
    private ImageView mSendButton;
    private EditText mReplyEdit;
    private Long postID;
    private TextView statusTextView;
    private String statusText;
    private RecyclerView mReplies;
    private FirebaseRecyclerAdapter<Replies,ReplyViewHolder> replyFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        mSendButton = (ImageView) findViewById(R.id.ivSend);
        mReplyEdit = (EditText) findViewById(R.id.etStatus);

        Intent intent = getIntent();
        postID =intent.getLongExtra(StatusActivity.POST_ID,0L);
        statusText = intent.getStringExtra(StatusActivity.STATUS_POST_DESCRIPTION);
        statusTextView = (TextView)findViewById(R.id.statusDescription);
        statusTextView.setText(statusText);
        statusRef = new Firebase("https://studdy-buddy.firebaseio.com/Status");
        mReplyRef = new Firebase("https://studdy-buddy.firebaseio.com/Reply");

       // mReplyRef = statusRef.limitToLast(50);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //@param (String Name, string userID, String Text, Long postID)
                Replies reply = new Replies(LogInActivity.personName, LogInActivity.personId, mReplyEdit.getText().toString(), postID); //last int must be changed to topic post ID number
                mReplyRef.push().setValue(reply, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            Log.e("FIREBASE", firebaseError.toString());
                        }
                    }
                });
                mReplyEdit.setText("");
            }
        });

        mReplies = (RecyclerView) findViewById(R.id.messagesList);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);

        mReplies.setHasFixedSize(false);
        mReplies.setLayoutManager(manager);

        mReplyQuery = mReplyRef.orderByChild("statusPostID").equalTo(postID);
        mStatusRefQuery = statusRef.orderByChild("postID").equalTo(postID);

        replyFirebaseAdapter = new FirebaseRecyclerAdapter<Replies,ReplyViewHolder>(Replies.class, R.layout.replies_card_view, ReplyViewHolder.class, mReplyQuery) {
            public ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.replies_card_view, parent, false);
                ReplyViewHolder holder = new ReplyViewHolder(v);
                return holder;
            }

            @Override
            public void populateViewHolder(ReplyViewHolder holder, Replies reply, int position) {

                holder.setName(reply.getName());
                holder.setStatus(reply.getStatus());
                holder.setIsSender(false);
            }
        };

        mReplies.setAdapter(replyFirebaseAdapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        replyFirebaseAdapter.cleanup();
    }

}

//}