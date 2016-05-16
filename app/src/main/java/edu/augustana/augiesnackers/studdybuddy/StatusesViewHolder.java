package edu.augustana.augiesnackers.studdybuddy;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nelly on 4/29/2016.
 */
public class StatusesViewHolder extends RecyclerView.ViewHolder {

    private CardView cv;
    TextView name;
    TextView description;
    TextView statusPostId;
    ImageView imageView;
    private Button attending_btn;
    private Button reply_btn;
    private Long replies;
    private Long attends;


    StatusesViewHolder(View itemView, final StatusActivity statusActivity) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        statusPostId = (TextView) itemView.findViewById(R.id.statusPostIDText);
        statusPostId.setVisibility(View.GONE);
        name = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
        reply_btn = (Button) itemView.findViewById(R.id.reply_btn);
        reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idString = statusPostId.getText().toString();
                Long postID = Long.parseLong(idString);

                statusActivity.openReplies(postID, description.getText().toString(), name.getText().toString());


            }
        });
        attending_btn = (Button) itemView.findViewById(R.id.attend_btn);
        attending_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idString = statusPostId.getText().toString();
                Long postID = Long.parseLong(idString);
                statusActivity.incrementAttendees(postID, StatusesViewHolder.this);

//                        Intent intent = new Intent(getApplicationContext(),ReplyActivity.class);
//                        startActivity(intent);
            }
        });
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    public void setName(String user) {
        name.setText(user);
    }

    public void setDescription(String desc, String tag) {
        description.setText(desc + " " + tag);
    }



    public void setPostID(Long id) {
        statusPostId.setText(id + "");
    }


    public void setReplyButton(Long num) {
        if (num > 0) {
            reply_btn.setText(num + " replies");
        }
    }

    public void setAttends(Long num) {
        if (num > 0) {
            attending_btn.setText(num + " attends");
        }
    }


}