package edu.augustana.augiesnackers.studdybuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class StatusActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private Firebase firebase;
    private EditText sendText;
    private ImageView sendbtn;
    private Query mStatusRef;
    private String userName;
    private String userID;


    FirebaseRecyclerAdapter<Status, StatusesViewHolder> firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        //TODO
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        sendText = (EditText) findViewById(R.id.etStatus);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userName = "Nelly Cheboi";//extras.getString(LogInActivity.USER_NAME);
        userID = "89";//extras.getString(LogInActivity.USER_ID);
        sendbtn = (ImageView) findViewById(R.id.ivSend);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlert();

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebase = new Firebase("https://studdy-buddy.firebaseio.com/Status");
        mStatusRef = firebase.orderByChild("timestamp").limitToLast(50);
        firebaseAdapter = new FirebaseRecyclerAdapter<Status, StatusesViewHolder>(Status.class, R.layout.status_card_view, StatusesViewHolder.class, mStatusRef) {
            @Override
            public StatusesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_card_view, parent, false);
                StatusesViewHolder holder = new StatusesViewHolder(v, StatusActivity.this);
                return holder;
            }

            @Override
            public void populateViewHolder(StatusesViewHolder holder, Status status, int position) {
                holder.setName(status.getName());
                holder.setDescription(status.getDescription(),status.getTag());
                holder.setImage(R.drawable.ic_facebook);
            }
        };
        recyclerView.setAdapter(firebaseAdapter);

        //TODO Firebase Stuff https://www.firebase.com/docs/android/guide/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAdapter.cleanup();
    }

public void openReplies(String text){
    Intent intent = new Intent(getApplicationContext(), ReplyActivity.class);
    startActivity(intent);
}
public void showAlert(){
    LayoutInflater li = LayoutInflater.from(this);
    View promptsView = li.inflate(R.layout.dialog_class_prompt, null);
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

    alertDialogBuilder.setView(promptsView);

    final EditText userInput = (EditText)promptsView.findViewById(R.id.dialog_class_promt_et);


    // set dialog message
    alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("DONE",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (isEntered(userInput)) {
                                String tag = "#" + userInput.getText();
                                Status status = new Status(userName, userID, sendText.getText().toString(), tag);

                                firebase.push().setValue(status, new Firebase.CompletionListener() {
                                    @Override
                                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                        if (firebaseError != null) {
                                            Log.e("Error", firebaseError.toString());
                                        }
                                    }
                                });
                                sendText.setText("");
                                dialog.dismiss();
                            }
                            // get user input and set it to result
                            // edit text

                        }
                    });


    // create alert dialog
    AlertDialog alertDialog = alertDialogBuilder.create();

    // show it
    alertDialog.show();

}
    public boolean isEntered(EditText passwordText){
        if(passwordText.getText().toString().trim().length() > 0) {
            return true;
        }
        return false;

    }
}


