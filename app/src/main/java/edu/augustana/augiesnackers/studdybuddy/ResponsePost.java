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

    //private TopicPost postTopic;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private ResponsePost() {
    }

    ResponsePost(String name, String uid, String message, String tag, String topicPost) {
        this.description = message;
        this.name = name;
        this.tag = tag;
        this.uid = uid;
        this.topicPost = topicPost;
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
}