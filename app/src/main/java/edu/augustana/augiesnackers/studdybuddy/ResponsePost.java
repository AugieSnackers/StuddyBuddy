package edu.augustana.augiesnackers.studdybuddy;

/**
 * Adapted by Scott Doberstein
 */
public class ResponsePost {
    String description;
    String name;
    String uid;
    String statusPostID; //make this a Status object pass in?
    int postID;

    //private TopicPost postTopic;

    // Required default constructor for Firebase object mapping
   @SuppressWarnings("unused")
    public ResponsePost() {
    }

    public ResponsePost(String name, String uid, String message, String statusPostID) { //add postID pass in later?
        this.description = message;
        this.name = name;
        this.uid = uid;
        this.statusPostID = statusPostID;
      //  this.postID = postID;
    }

    public String getDescription() {
        return description;
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