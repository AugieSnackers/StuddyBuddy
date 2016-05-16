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

    /**
     * constructor for Replies class
     *
     * @param name         string representing user name
     * @param userId       string representing users ID
     * @param message      string representing what text the user is posting
     * @param statusPostID Long integer representing the ID for the post
     */
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

    public Long getStatusPostID() {
        return statusPostID;
    }

}