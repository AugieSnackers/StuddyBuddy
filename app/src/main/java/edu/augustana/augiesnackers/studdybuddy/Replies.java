package edu.augustana.augiesnackers.studdybuddy;

/**
 * Adapted by Scott Doberstein
 */
public class Replies {
    String status;
    String name;
    String uid;
    String statusPostID; //make this a Status object pass in?
    int postID;

    //private TopicPost postTopic;

    // Required default constructor for Firebase object mapping
   @SuppressWarnings("unused")
    public Replies() {
    }

    public Replies(String name, String uid, String message, String statusPostID) { //add postID pass in later?
        this.status = message;
        this.name = name;
        this.uid = uid;
        this.statusPostID = statusPostID;
      //  this.postID = postID;
    }

    public String getStatus() {
        return status;
    }
    public String getName() {
        return name;
    }
    public String getUid() {
        return uid;
    }
    public int getPostID(){ return postID; }
    public String statusPostID(){
        return statusPostID;
    }

}