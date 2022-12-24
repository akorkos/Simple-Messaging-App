import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.security.SecureRandom;

public class Message {
    private boolean isRead;
    private final String sender,  body, id;
    private static final char[] ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * Represents a message. Also, assigns a unique id for the message and marks the message
     * as unread
     * @param sender of the message
     * @param body content of the message
     */
    Message(String sender, String body){
        this.sender = sender;
        this.body = body;
        isRead = false;
        id = NanoIdUtils.randomNanoId(new SecureRandom(), ALPHABET, 4);
    }

    /**
     * @return the message sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @return if the message is read or not
     */
    public boolean isRead(){
        return isRead;
    }

    /**
     * @return the unique id of the message
     */
    public String getId(){
        return id;
    }

    /**
     * Marks the message as read
     */
    public void read(){
        isRead = true;
    }

    @Override
    public String toString(){
        return "(" + this.sender + ") " + this.body;
    }
}
