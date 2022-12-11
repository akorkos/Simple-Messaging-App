import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    private String username;
    private String authToken;
    private MessageBox inbox;

    Account(String username, String authToken){
        this.username = username;
        this.authToken = authToken;
        inbox = new MessageBox();
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
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
        inbox.addMessage(new Message(sender, username, body));
    }
}
