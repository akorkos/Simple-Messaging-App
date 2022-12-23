import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.security.SecureRandom;

public class Message {
    private boolean isRead;
    private final String sender,  body, id;
    private static final char[] ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    Message(String sender, String body){
        this.sender = sender;
        this.body = body;
        isRead = false;
        id = NanoIdUtils.randomNanoId(new SecureRandom(), ALPHABET, 4);
    }

    public String getSender() {
        return sender;
    }

    public boolean isRead(){
        return isRead;
    }

    public String getId(){
        return id;
    }

    public void read(){
        isRead = true;
    }

    @Override
    public String toString(){
        return "(" + this.sender + ") " + this.body;
    }
}
