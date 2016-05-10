package edu.augustana.augiesnackers.studdybuddy;

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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class ReplyActivity extends AppCompatActivity {
    private Firebase mRef;
    private Query mReplyRef;
    private ImageView mSendButton;
    private EditText mReplyEdit;

    private RecyclerView mReplies;
    private FirebaseRecyclerAdapter<Replies,ReplyViewHolder> mRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        mSendButton = (ImageView) findViewById(R.id.ivSend);
        mReplyEdit = (EditText) findViewById(R.id.etStatus);


        mRef = new Firebase("https://studdy-buddy.firebaseio.com/Reply");
        mReplyRef = mRef.limitToLast(50);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Replies reply = new Replies("", "", mReplyEdit.getText().toString(), "Status Post ID"); //last int must be changed to topic post ID number
                mRef.push().setValue(reply, new Firebase.CompletionListener() {
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

        mRecycleViewAdapter = new FirebaseRecyclerAdapter<Replies,ReplyViewHolder>(Replies.class, R.layout.replies_card_view, ReplyViewHolder.class, mReplyRef) {
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

        mReplies.setAdapter(mRecycleViewAdapter);
    }

}

//}