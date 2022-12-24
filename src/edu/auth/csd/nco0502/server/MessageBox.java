package edu.auth.csd.nco0502.server;

import edu.auth.csd.nco0502.server.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageBox  {
    private final ArrayList<Message> messageBox;

    /**
     * edu.auth.csd.nco0502.server.Message inbox. Messages are saved in a list
     */
    MessageBox() {
        messageBox = new ArrayList<>();
    }

    /**
     * Searches for a specific message in the inbox
     * @param id of the message to search
     * @return the message
     */
    private Message findMessage(String id) {
        return messageBox.stream().filter(msg -> id.equals(msg.getId())).findFirst().orElse(null);
    }

    /**
     * @return all messages of the inbox
     */
    public List<Message> getAllMessages() {
        return messageBox.stream().toList();
    }

    /**
     * Reads a specific message if its in the inbox
     * @param id of the message
     * @return the message itself
     */
    public Message readMessage(String id) {
        Message message = findMessage(id);
        if (message != null) {
            int index = messageBox.indexOf(message);
            message.read();
            messageBox.set(index, message);
        }
        return message;
    }

    /**
     * Adds a new message in the inbox
     * @param message to be added
     */
    public void addMessage(Message message) {
        messageBox.add(message);
    }

    /**
     * Deletes a specific message from the inbox
     * @param id of the message
     * @return if the operation was successful or not
     */
    public boolean deleteMessage(String id) {
        Message message = findMessage(id);
        if (message != null)
            return messageBox.remove(message);
        return false;
    }
}
