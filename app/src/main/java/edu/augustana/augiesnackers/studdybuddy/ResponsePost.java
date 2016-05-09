package edu.augustana.augiesnackers.studdybuddy;

/**
 * Adapted by Scott Doberstein
 */
public class ResponsePost {
    String description;
    String name;
    String uid;
    int topicPostID; //make this a Status object pass in?
    int postID;

    //private TopicPost postTopic;

    // Required default constructor for Firebase object mapping
   @SuppressWarnings("unused")
    public ResponsePost() {
    }

    ResponsePost(String name, String uid, String message, int topicPostID) { //add postID pass in later?
        this.description = message;
        this.name = name;
        this.uid = uid;
        this.topicPostID = topicPostID;
        this.postID = postID;
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
    public int topicPostID(){
        return topicPostID;
    }

}