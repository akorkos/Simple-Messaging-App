import java.util.ArrayList;
import java.util.List;

public class MessageBox  {
    private ArrayList<Message> messageBox;

    MessageBox() {
        messageBox = new ArrayList<>();
    }

    private Message findMessage(String id) {
        return messageBox.stream().filter(msg -> id.equals(msg.getId())).findFirst().orElse(null);
    }

    public List<Message> getAllMessages() {
        return messageBox.stream().toList();
    }

    public Message readMessage(String id) {
        Message message = findMessage(id);
        if (message != null) {
            int index = messageBox.indexOf(message);
            message.read();
            messageBox.set(index, message);
        }
        return message;
    }

    public void addMessage(Message message) {
        messageBox.add(message);
    }

    public boolean deleteMessage(String id) {
        Message message = findMessage(id);
        if (message != null)
            return messageBox.remove(message);
        return false;
    }

}
