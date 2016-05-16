package edu.augustana.augiesnackers.studdybuddy;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Query;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * author Nelly and Scott
 * This class displays the statuses in a card view, it uses recycleview, card view and firebase adapter from the Firebase UI library
 */
public class StatusActivity extends AppCompatActivity {


    private Firebase statusRef;
    private Query mStatusQuery;

    private EditText sendText;
    private ImageView sendbtn;

    private String userName;
    private String userID;

    //for intents
    final static String POST_ID ="postid";
    final static String STATUS_POST_DESCRIPTION ="status";
    final static String DESC_CREATOR ="creator";

    private View popupViewAbout;
    private PopupWindow popupWindowAbout;

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Status, StatusesViewHolder> firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        sendText = (EditText) findViewById(R.id.etStatus);
        userName = LogInActivity.personName;
        userID = LogInActivity.personId;
        sendbtn = (ImageView) findViewById(R.id.ivSend);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlert();

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        statusRef = new Firebase("https://studdy-buddy.firebaseio.com/Status");
        mStatusQuery= statusRef.limitToLast(10);
        //firebaseAdapter of the Firebase UI library conveniently populates the card as desired.
        firebaseAdapter = new FirebaseRecyclerAdapter<Status, StatusesViewHolder>(Status.class, R.layout.status_card_view, StatusesViewHolder.class, mStatusQuery) {
            /**
             * define each card
             * @param parent
             * @param viewType
             * @return
             */
            @Override
            public StatusesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_card_view, parent, false);
                StatusesViewHolder holder = new StatusesViewHolder(v, StatusActivity.this);
                return holder;
            }

            /**
             * populates the members of various cards
             * @param holder
             * @param status
             * @param position
             */
            @Override
            public void populateViewHolder(StatusesViewHolder holder, Status status, int position) {
                holder.setPostID(status.getPostID());
                holder.setName(status.getName());
                holder.setDescription(status.getDescription(),status.getTag());
                holder.setReplyButton(status.getNumReplies());
                holder.setAttends(status.getNumAttendees());

            }
        };
        recyclerView.setAdapter(firebaseAdapter);


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
        if (id == R.id.action_about) {
            showAboutPage();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAdapter.cleanup();
    }

    /**
     * called by the StatusesViewHolder, to sart the reply activity when the reply button is pressed
     * @param postID
     * @param description
     * @param creator
     */
    public void openReplies(Long postID, String description, String creator) {
        Intent intent = new Intent(getApplicationContext(), ReplyActivity.class);
        intent.putExtra(POST_ID, postID);
        intent.putExtra(STATUS_POST_DESCRIPTION, description);
        intent.putExtra(DESC_CREATOR, creator);
        startActivity(intent);
    }

    /**
     * requres the user to enter the class id for the particular post
     */
    public void showAlert() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_class_prompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.dialog_class_promt_et);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("DONE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (isEntered(userInput)) {

                                    String tag = "#" + userInput.getText();
                                    incrementAndPost(sendText.getText().toString(), tag);

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

    /**
     * Check whether the post was entered
     * @param passwordText
     * @return
     */
    public boolean isEntered(EditText passwordText) {
        if (passwordText.getText().toString().trim().length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * increment the number of attendees to a particular discussion
     * @param postID
     * @param holder
     */
    public void incrementAttendees(Long postID, final StatusesViewHolder holder) {

        Query mStatusRefQuery = statusRef.orderByChild("postID").equalTo(postID);

        mStatusRefQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                DataSnapshot firstChild = snapshot.getChildren().iterator().next();
                Firebase ref = new Firebase("https://studdy-buddy.firebaseio.com/Status/" + firstChild.getKey() + "/numAttendees");


                ref.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData currentData) {

                        currentData.setValue((Long) currentData.getValue() + 1);

                        return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                        Long num = currentData.getValue(Long.class);
                        holder.setAttends(num);

                    }


                });

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




            }

    /**
     * increment the number of posts in the database before pushing the posts to the database
     * @param description, the status
     * @param tag, the class/course
     */
            public void incrementAndPost(final String description, final String tag) {
                Firebase ref = new Firebase("https://studdy-buddy.firebaseio.com/PostID");

                ref.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData currentData) {
                        if (currentData.getValue() == null) {
                            currentData.setValue(1);
                        } else {
                            currentData.setValue((Long) currentData.getValue() + 1);
                            Status.postCount = (Long) currentData.getValue();
                        }
                        return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {

                        Status status = new Status(userName, userID, description, tag, Status.postCount, 0L, 0L);
                        statusRef.push().setValue(status, new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                if (firebaseError != null) {
                                    Log.e("Error", firebaseError.toString());
                                }
                            }
                        });

                    }
                });

            }


    /**
     * pop up window with the details of the developers
     */
    private void showAboutPage() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout0);
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        popupViewAbout = layoutInflater.inflate(R.layout.activity_about, null);
        popupWindowAbout = new PopupWindow(popupViewAbout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindowAbout.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        Button closeButton = (Button) popupViewAbout.findViewById(R.id.buttonAbout);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowAbout.dismiss();
            }
        });
    }
        }



