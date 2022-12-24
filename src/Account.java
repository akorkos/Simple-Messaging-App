import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.List;

public class Account implements Serializable {
    private final String username;
    private final int authToken;
    private final MessageBox inbox;
    private static final char[] ALPHABET = "0123456789".toCharArray();

    /**
     * Creates a new user account with a specific username given by the user. The constructor, creates an empty message
     * inbox and assigns a unique auth token for the user (the auth token is used for every function expect for the
     * first one)
     * @param username for the new account
     */
    Account(String username){
        this.username = username;
        authToken = Integer.parseInt(NanoIdUtils.randomNanoId(new SecureRandom(), ALPHABET, 4));
        inbox = new MessageBox();
    }

    Account(String username, int authToken){
        this.username = username;
        this.authToken = authToken;
        inbox = new MessageBox();
    }

    /**
     * @return the username of an account
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the authentication token for an account
     */
    public int getAuthToken() {
        return authToken;
    }

    /**
     * @return the message inbox of an account
     */
    public List<Message> getInbox(){
        return inbox.getAllMessages();
    }

    /**
     * Deletes a specific message
     * @param id of the message to be deleted
     * @return if the operation was successful or not
     */
    public boolean deleteMessage(String id){
        return inbox.deleteMessage(id);
    }

    /**
     * Reads a specific message
     * @param id of the message to be read
     * @return the content of the message
     */
    public Message readMessage(String id){
        return inbox.readMessage(id);
    }

    /**
     * Adds a new message in this accounts inbox
     * @param sender of the message
     * @param body content of the message
     */
    public void addMessage(String sender, String body){
        inbox.addMessage(new Message(sender, body));
    }

    @Override
    public String toString(){
        return this.username;
    }
}
