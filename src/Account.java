import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.List;

public class Account implements Serializable {
    private final String username;
    private final int authToken;
    private final MessageBox inbox;
    private static final char[] ALPHABET = "0123456789".toCharArray();

    Account(String username){
        this.username = username;
        authToken = Integer.parseInt(NanoIdUtils.randomNanoId(new SecureRandom(), ALPHABET, 4));
        inbox = new MessageBox();
    }

    public String getUsername() {
        return username;
    }

    public int getAuthToken() {
        return authToken;
    }

    public List<Message> getInbox(){
        return inbox.getAllMessages();
    }

    public boolean deleteMessage(String id){
        return inbox.deleteMessage(id);
    }

    public Message readMessage(String id){
        return inbox.readMessage(id);
    }

    public void addMessage(String sender, String body){
        inbox.addMessage(new Message(sender, body));
    }

    @Override
    public String toString(){
        return this.username;
    }
}
