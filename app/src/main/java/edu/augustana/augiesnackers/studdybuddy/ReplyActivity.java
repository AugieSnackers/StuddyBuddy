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
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * This class creates and saves the replies to particular statuses. It uses the firebaseadapter from the Firebase UI library along with card and recycle views
 *
 * @author Nelly and Scott
 */
public class ReplyActivity extends AppCompatActivity {
    private Firebase statusRef;
    private Firebase mReplyRef;

    private Query mReplyQuery;
    private Query mStatusRefQuery;

    private ImageView mSendButton;
    private EditText mReplyEdit;
    private TextView statusTextView;
    private TextView nameTextView;
    private Button delete_btn;

    private String statusText;
    private Long postID;
    private String postUserName;

    private RecyclerView mReplies;
    private FirebaseRecyclerAdapter<Replies, ReplyViewHolder> replyFirebaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        //Extract data sent over from status activity class
        Intent intent = getIntent();
        postID = intent.getLongExtra(StatusActivity.POST_ID, 0L);
        statusText = intent.getStringExtra(StatusActivity.STATUS_POST_DESCRIPTION);
        postUserName = intent.getStringExtra(StatusActivity.DESC_CREATOR);

        statusTextView = (TextView) findViewById(R.id.statusDescription);
        statusTextView.setText(statusText);
        nameTextView = (TextView) findViewById(R.id.userName);
        nameTextView.setText(postUserName);
        mReplyEdit = (EditText) findViewById(R.id.etStatus);

        //is it the creator of the post replying to their own post
        boolean isSenderClicked = postUserName.equals(LogInActivity.personName);

//whether or not to display the delete button
        delete_btn = (Button) findViewById(R.id.delete_btn);
        if (isSenderClicked) {
            delete_btn.setVisibility(View.VISIBLE);
        } else {
            delete_btn.setVisibility(View.GONE);
        }

//deletes the status and all the replies to that status
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusRefQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        DataSnapshot firstChild = snapshot.getChildren().iterator().next();
                        Firebase ref = new Firebase("https://studdy-buddy.firebaseio.com/Status/" + firstChild.getKey());
                        ref.removeValue();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                ReplyActivity.this.finish();
            }
        });
        statusRef = new Firebase("https://studdy-buddy.firebaseio.com/Status");
        mReplyRef = new Firebase("https://studdy-buddy.firebaseio.com/Reply");
        //get reply with the given post id
        mReplyQuery = mReplyRef.orderByChild("statusPostID").equalTo(postID);
        //get status with the given post iid
        mStatusRefQuery = statusRef.orderByChild("postID").equalTo(postID);

        mReplies = (RecyclerView) findViewById(R.id.messagesList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);
        mReplies.setHasFixedSize(false);
        mReplies.setLayoutManager(manager);

        //fill the recycle view with the replies, putting the one send by the creator on the right and the one send by others on the left
        //Uses FirebaseAdapter from the FirabaseUI library
        replyFirebaseAdapter = new FirebaseRecyclerAdapter<Replies, ReplyViewHolder>(Replies.class, R.layout.replies_card_view, ReplyViewHolder.class, mReplyQuery) {
            public ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.replies_card_view, parent, false);
                ReplyViewHolder holder = new ReplyViewHolder(v);
                return holder;
            }

            @Override
            public void populateViewHolder(ReplyViewHolder holder, Replies reply, int position) {
                boolean isSender = reply.getName().equals(LogInActivity.personName);
                holder.setName(reply.getName());
                holder.setStatus(reply.getStatus());
                holder.setIsSender(isSender);
            }
        };
        mReplies.setAdapter(replyFirebaseAdapter);
        mSendButton = (ImageView) findViewById(R.id.ivSend);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                incrementAndPost();
            }
        });
    }

    /**
     * clear the recycle view
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        replyFirebaseAdapter.cleanup();
    }

    /**
     * increment the number of replies for a particular post and  post the current reply
     */
    public void incrementAndPost() {
        mStatusRefQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                DataSnapshot firstChild = snapshot.getChildren().iterator().next();
                Firebase ref = new Firebase("https://studdy-buddy.firebaseio.com/Status/" + firstChild.getKey() + "/numReplies");
                ref.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData currentData) {
                        Log.d("Num replies", "" + currentData.getValue());
                        currentData.setValue((Long) currentData.getValue() + 1);
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

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


    }
}
