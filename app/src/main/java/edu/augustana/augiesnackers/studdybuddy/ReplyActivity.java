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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Query;
import com.firebase.client.Transaction;
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
    private TextView nameTextView;
    private String statusText;
    private Button delete_btn;
    private RecyclerView mReplies;
    boolean isSender;
    private FirebaseRecyclerAdapter<Replies,ReplyViewHolder> replyFirebaseAdapter;
    private String descUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);


        mReplyEdit = (EditText) findViewById(R.id.etStatus);

        Intent intent = getIntent();
        postID =intent.getLongExtra(StatusActivity.POST_ID, 0L);
        statusText = intent.getStringExtra(StatusActivity.STATUS_POST_DESCRIPTION);
        descUserName =intent.getStringExtra(StatusActivity.DESC_CREATOR);

        statusTextView = (TextView)findViewById(R.id.statusDescription);
        statusTextView.setText(statusText);

        nameTextView = (TextView)findViewById(R.id.userName);
        nameTextView.setText(descUserName);

       boolean isSenderClicked = descUserName.equals(LogInActivity.personName);

        delete_btn = (Button)findViewById(R.id.delete_btn);
        delete_btn.setEnabled(isSenderClicked);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusRefQuery.getRef().removeValue();
                mReplyQuery.getRef().removeValue();
                ReplyActivity.this.finish();
            }
        });

        statusRef = new Firebase("https://studdy-buddy.firebaseio.com/Status");
        mReplyRef = new Firebase("https://studdy-buddy.firebaseio.com/Reply");
        mReplyQuery = mReplyRef.orderByChild("statusPostID").equalTo(postID);
        mStatusRefQuery = statusRef.orderByChild("postID").equalTo(postID);


        mReplies = (RecyclerView) findViewById(R.id.messagesList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);
        mReplies.setHasFixedSize(false);
        mReplies.setLayoutManager(manager);
        replyFirebaseAdapter = new FirebaseRecyclerAdapter<Replies,ReplyViewHolder>(Replies.class, R.layout.replies_card_view, ReplyViewHolder.class, mReplyQuery) {
            public ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.replies_card_view, parent, false);
                ReplyViewHolder holder = new ReplyViewHolder(v);
                return holder;
            }

            @Override
            public void populateViewHolder(ReplyViewHolder holder, Replies reply, int position) {
                isSender = reply.getName().equals(LogInActivity.personName);
                holder.setName(reply.getName());
                holder.setStatus(reply.getStatus());
                holder.setIsSender(isSender);
            }
        };
        mReplies.setAdapter(replyFirebaseAdapter);

        // mReplyRef = statusRef.limitToLast(50);
        mSendButton = (ImageView) findViewById(R.id.ivSend);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                incrementAndPost();
            }
        });



    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        replyFirebaseAdapter.cleanup();
    }
    public void incrementAndPost(){
        //TODO NOT WORKING YET
        Firebase ref = mStatusRefQuery.limitToFirst(1).getRef();

        ref.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);

                }
                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {

                Replies reply = new Replies(LogInActivity.personName, LogInActivity.personId, mReplyEdit.getText().toString(), postID);
                mReplyRef.push().setValue(reply, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            Log.e("Error", firebaseError.toString());
                        }
                    }
                });
                mReplyEdit.setText("");
            }
        });

    }
}

//}