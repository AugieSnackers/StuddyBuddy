package edu.augustana.augiesnackers.studdybuddy;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nelly on 4/29/2016.
 * this class is used contain the instance of each card. Needed especially when populating the views of this card with the corresponding details
 */

public class StatusesViewHolder extends RecyclerView.ViewHolder {

    private CardView cv;

    private TextView name;
    private TextView description;
    private TextView statusPostId;
    private ImageView imageView;

    private Button attending_btn;
    private Button reply_btn;



    StatusesViewHolder(View itemView, final StatusActivity statusActivity) {
        super(itemView);

        cv = (CardView) itemView.findViewById(R.id.cardView);
        //post id
        statusPostId = (TextView) itemView.findViewById(R.id.statusPostIDText);
        statusPostId.setVisibility(View.GONE);
        //creator
        name = (TextView) itemView.findViewById(R.id.title);
        //status
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