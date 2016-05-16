package edu.augustana.augiesnackers.studdybuddy;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;

/**
 * Created by Nelly on 4/28/2016.
 * status class with members of the particular class
 */
public class Status {

    public static long postCount = 0;

    String description;
    String name;
    String tag;
    String uid;
    Long postID;
    Long numReplies;
    Long numAttendees;

    @SuppressWarnings("unused")
    public Status() {

    }

    /**
     * constructor for the Status class
     *
     * @param name         string that shows name of user
     * @param uid          string that shows the users ID number
     * @param message      string that shows message that was typed by user
     * @param tag          string that shows tag user used
     * @param postID       long integer that shows id for the post
     * @param numReplies   long integer that shows the number of replies the user got from specific post
     * @param numAttendees long integer that shows the number of users who attended the study session from users post
     */
    public Status(String name, String uid, String message, String tag, Long postID, Long numReplies, Long numAttendees) { //add post id pass in later
        this.description = message;
        this.name = name;
        this.tag = tag;
        this.uid = uid;
        this.postID = postID;
        this.numReplies = numReplies;
        this.numAttendees = numAttendees;

    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getUid() {
        return uid;
    }

    public Long getPostID() {
        return postID;
    }

    public Long getNumReplies() {
        return numReplies;
    }

    public Long getNumAttendees() {
        return numAttendees;
    }

}
