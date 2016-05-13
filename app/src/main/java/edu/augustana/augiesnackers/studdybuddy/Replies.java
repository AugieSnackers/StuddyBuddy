package edu.augustana.augiesnackers.studdybuddy;

/**
 * Adapted by Scott Doberstein
 */
public class Replies {
    String status;
    String name;
    String userId;
    Long statusPostID;




    // Required default constructor for Firebase object mapping
   @SuppressWarnings("unused")
    public Replies() {
    }

    public Replies(String name, String userId, String message, Long statusPostID) {
        this.status = message;
        this.name = name;
        this.userId = userId;
        this.statusPostID = statusPostID;

    }

    public String getStatus() {
        return status;
    }
    public String getName() {
        return name;
    }
    public String getUserId() {
        return userId;
    }
    public Long getStatusPostID(){
        return statusPostID;
    }

}