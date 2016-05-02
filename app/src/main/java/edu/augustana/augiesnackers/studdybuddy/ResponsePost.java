package edu.augustana.augiesnackers.studdybuddy;

/**
 * @author greg
 * @since 6/21/13
 * Adapted by Scott Doberstein
 */
public class ResponsePost {
    private int postID;
    private String message;
    private String author;
    private int topicID;
    private int timeStamp;

    //private TopicPost postTopic;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private ResponsePost() {
    }

    ResponsePost(String message, String author, int postID, int topicID) {
        this.postID = postID;
        this.message = message;
        this.author = author;
        this.topicID = topicID;
    }


    public int getPostID(){
        return postID;
    }
    public String getMessage() {
        return message;
    }
    public String getAuthor() {
        return author;
    }
    public int getTopicID(){
        return topicID;
    }
}