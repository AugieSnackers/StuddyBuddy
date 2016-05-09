package edu.augustana.augiesnackers.studdybuddy;

/**
 * Adapted by Scott Doberstein
 */
public class ResponsePost {
    String description;
    String name;
    String tag;
    String uid;
    String topicPost; //make this a Status object pass in?
    int postID;

    //private TopicPost postTopic;

    // Required default constructor for Firebase object mapping
   @SuppressWarnings("unused")
    public ResponsePost() {
    }

    ResponsePost(String name, String uid, String message, String tag, String topicPost) { //add postID pass in later?
        this.description = message;
        this.name = name;
        this.tag = tag;
        this.uid = uid;
        this.topicPost = topicPost;
        this.postID = postID;
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
    public int getPostID(){ return postID; }

}