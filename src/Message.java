import me.nimavat.shortid.ShortId;

import java.util.Objects;

public class Message {
    private Boolean isRead;
    private String sender, receiver, body, id;

    Message(String sender, String receiver, String body){
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        isRead = false;
        id = ShortId.generate();
    }

    public String getBody() {
        return body;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
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

}
