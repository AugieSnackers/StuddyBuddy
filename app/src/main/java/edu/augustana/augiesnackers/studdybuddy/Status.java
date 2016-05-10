package edu.augustana.augiesnackers.studdybuddy;

/**
 * Created by Nelly on 4/28/2016.
 */
public class Status {
    String description;
    String name;
    String tag;
    String uid;
    int postID;
    @SuppressWarnings("unused")
    public Status(){

    }
    public  Status(String name,String uid,String message, String tag){ //add post id pass in later
        this.description = message;
        this.name = name;
        this.tag =tag;
        this.uid = uid;
        //this.postID = postID;

    }

    public String getDescription(){
        return description;   }
    public String getName(){
        return name;   }
    public String getTag(){
        return tag;   }
    public String getUid(){
        return uid;
    }
    public int getPostID(){
        return postID;
    }


}
