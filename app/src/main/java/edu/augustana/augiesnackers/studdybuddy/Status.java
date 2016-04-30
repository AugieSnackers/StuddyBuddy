package edu.augustana.augiesnackers.studdybuddy;

/**
 * Created by Nelly on 4/28/2016.
 */
public class Status {
    String message;
    String name;
    String tag;
    String uid;
    public  Status(String name, String uid,String message, String tag){
        this.message = message;
        this.name = name;
        this.tag =tag;
        this.uid = uid;

    }
    public String getMessage(){
        return message;   }
    public String getName(){
        return name;   }
    public String getTag(){
        return tag;   }
    public String getUid(){
        return uid;
    }

}
