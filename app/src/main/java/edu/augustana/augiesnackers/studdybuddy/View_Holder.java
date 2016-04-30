package edu.augustana.augiesnackers.studdybuddy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.CardView;
/**
 * Created by Nelly on 4/29/2016.
 */
public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView name;
    TextView description;
    ImageView imageView;
    Button attending_btn;
    Button reply_btn;

    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        name = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
        reply_btn = (Button)itemView.findViewById(R.id.reply_btn);
        attending_btn = (Button)itemView.findViewById(R.id.attend_btn);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
