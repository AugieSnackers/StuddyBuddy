package edu.augustana.augiesnackers.studdybuddy;

/**
 * Adapted by Scott Doberstein
 */
public class TopicPost {
    private int postID;
    private String message;
    private String author;
    private String postTags;
    private int timeStamp;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private TopicPost() {
    }

    TopicPost(String message, String author, String postTags, int postID) {
        this.postID = postID;
        this.message = message;
        this.author = author;
        this.postTags = postTags;
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
    public String getPostTags(){
        return postTags;
    }
}