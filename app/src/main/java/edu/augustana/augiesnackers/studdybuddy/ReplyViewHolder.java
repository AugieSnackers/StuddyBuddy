package edu.augustana.augiesnackers.studdybuddy;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Nelly on 5/9/2016.
 */
public class ReplyViewHolder extends RecyclerView.ViewHolder{
    View mView;

    public ReplyViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

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



    public void setName(String name) {
        TextView field = (TextView) mView.findViewById(R.id.name_text);
        field.setText(name);
    }

    public void setStatus(String text) {
        TextView field = (TextView) mView.findViewById(R.id.message_text);
        field.setText(text);
    }


}

