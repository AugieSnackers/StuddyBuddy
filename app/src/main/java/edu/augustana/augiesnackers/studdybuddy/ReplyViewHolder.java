package edu.augustana.augiesnackers.studdybuddy;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * adapted from https://github.com/firebase/FirebaseUI-Android/blob/master/app/src/main/java/com/firebase/uidemo/RecyclerViewDemoActivity.java
 * Created by Nelly on 5/9/2016.
 */
public class ReplyViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public ReplyViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    /**
     * display the replies in a chat way
     * @param isSender
     */
    public void setIsSender(boolean isSender) {


        FrameLayout left_arrow = (FrameLayout) mView.findViewById(R.id.left_arrow);
        FrameLayout right_arrow = (FrameLayout) mView.findViewById(R.id.right_arrow);
        RelativeLayout messageContainer = (RelativeLayout) mView.findViewById(R.id.message_container);
        LinearLayout message = (LinearLayout) mView.findViewById(R.id.message);


        if (isSender) {
            left_arrow.setVisibility(View.GONE);
            right_arrow.setVisibility(View.VISIBLE);
            messageContainer.setGravity(Gravity.RIGHT);
        } else {
            left_arrow.setVisibility(View.VISIBLE);
            right_arrow.setVisibility(View.GONE);
            messageContainer.setGravity(Gravity.LEFT);
        }
    }

    /**
     * set the name of the reply in the view
     * @param name
     */
    public void setName(String name) {
        TextView field = (TextView) mView.findViewById(R.id.name_text);
        field.setText(name);
    }

    /**
     * set the content of the reply
     * @param text
     */
    public void setStatus(String text) {
        TextView field = (TextView) mView.findViewById(R.id.message_text);
        field.setText(text);
    }



}

