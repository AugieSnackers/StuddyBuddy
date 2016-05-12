package edu.augustana.augiesnackers.studdybuddy;

/**
 * Adapted by Scott Doberstein
 */
public class Replies {
    String status;
    String name;
    String uid;
    Long statusPostID;


    //private TopicPost postTopic;

    // Required default constructor for Firebase object mapping
   @SuppressWarnings("unused")
    public Replies() {
    }

    public Replies(String name, String uid, String message, Long statusPostID) {
        this.status = message;
        this.name = name;
        this.uid = uid;
        this.statusPostID = statusPostID;

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
    public Long getStatusPostID(){
        return statusPostID;
    }

}