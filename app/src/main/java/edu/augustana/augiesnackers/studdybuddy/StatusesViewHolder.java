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
    ImageView imageView;
    private Button attending_btn;
    private Button reply_btn;
    private int replies =0;
    private int attends =0;

    StatusesViewHolder(View itemView, final StatusActivity mainActivity) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);

        name = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
        reply_btn = (Button) itemView.findViewById(R.id.reply_btn);
        reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replies++;
                reply_btn.setText(replies + " Replies");
                mainActivity.openReplies(description.getText()+"");

            }
        });
        attending_btn = (Button) itemView.findViewById(R.id.attend_btn);
        attending_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attends++;
                attending_btn.setText(attends +" Attends");
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
        description.setText(desc+" "+tag);
    }

    public void setImage(int id) {
        imageView.setImageResource(id);
    }
}
