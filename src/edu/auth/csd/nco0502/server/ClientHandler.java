package edu.auth.csd.nco0502.server;

import edu.auth.csd.nco0502.common.Request;
import edu.auth.csd.nco0502.server.Account;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler extends Thread{

    private final Socket socket;
    private final ServerHandler server;
    private ObjectInputStream input;
    private PrintWriter output;
    private ArrayList<Integer> tokensUsed;

    /**
     * Handler for each client
     * @param socket of the client - server communication
     * @param server handler of the server
     */
    ClientHandler(Socket socket, ServerHandler server){
        tokensUsed = new ArrayList<>();
        tokensUsed.add(1000);
        tokensUsed.add(1001);
        tokensUsed.add(1002);

        this.socket = socket;
        this.server = server;
        try {
            input = new ObjectInputStream(this.socket.getInputStream());
            output = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Executes a specific function, as requested
     */
    @Override
    public void run() {
        try {
            Request request = (Request) input.readObject();

            switch (request.getFid()) {
                case 1 -> createAccount(request.getArgs());
                case 2 -> showAccounts(request.getArgs());
                case 3 -> sendMessage(request.getArgs());
                case 4 -> showInbox(request.getArgs());
                case 5 -> readMessage(request.getArgs());
                case 6 -> deleteMessage(request.getArgs());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Socket could not be closed");
                System.exit(1);
            }
        }
    }

    /**
     * Validates a username. A username should only contain roman letters, 
     * numbers and the underscore
     * @param username to be validated
     * @return if the username is valid or not
     */
    private boolean isValid(String username){
        Pattern usernamePattern = Pattern.compile("([0-9a-zA-Z_])+");
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.matches();
    }

    private void createAccount(String[] args){
        if (args.length == 1)
            createAccount(args[0]);
        else
            output.println("Incorrect number of arguments");
    }

    /**
     * Creates a new account with the username provided. The method also checks,
     * if the username is valid and if the username is already in use. If 
     * everything checks out, it sends to the client the auth token of the new 
     * user.
     * @param username for the new account
     */
    private void createAccount(String username){
        if (!isValid(username))
            output.println("Invalid Username");
        else if (server.accountExists(username))
            output.println("Sorry, the user already exists");
        else {
            Account account = new Account(username, tokensUsed);
            server.addAccount(account);
            tokensUsed.add(account.getAuthToken());
            output.println(account.getAuthToken());
        }
    }

    /**
     * Authenticates the user and shows all registered accounts
     * @param args containing the auth token
     */
    private void showAccounts(String[] args){
        if (args.length != 1)
            output.println("Incorrect number of arguments");
        else if (server.authenticate(Integer.parseInt(args[0])))
            showAccounts();
        else
            output.println("Invalid Auth Token");
    }

    /**
     * Prints all registered users
     */
    private void showAccounts(){
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < server.getAccounts().size(); i++)
            out.append(i + 1).append(". ").append(server.getAccounts().get(i)).append("\n");
        output.println(out);
    }

    /**
     * Authenticates the user and send a message
     * @param args contains the auth token, recipient username and message text
     */
    private void sendMessage(String[] args){
        if (args.length != 3)
            output.println("Incorrect number of arguments");
        else if (server.authenticate(Integer.parseInt(args[0])))
            sendMessage(Integer.parseInt(args[0]), args[1], args[2]);
        else
            output.println("Invalid Auth Token");
    }

    /**
     * Sends a message to a specific recipient
     * @param recipient that receives the message
     * @param message content
     */
    private void sendMessage(int authToken, String recipient, String message){
        Account recipientAccount = server.getAccount(recipient);
        String sender = String.valueOf(server.getAccount(authToken));

        if (recipientAccount == null)
            output.println("User does not exist");
        else {
            recipientAccount.addMessage(sender, message);
            output.println("OK");
        }
    }

    /**
     * Authenticates the user and shows all the messages received
     * @param args contains the auth token
     */
    private void showInbox(String[] args){
        if (args.length != 1)
            output.println("Incorrect number of arguments");
        else if (server.authenticate(Integer.parseInt(args[0])))
            showInbox(Integer.parseInt(args[0]));
        else
            output.println("Invalid Auth Token");
    }

    /**
     * Prints the inbox of a user. If a message is read a * is added
     * @param authToken of the user
     */
    private void showInbox(int authToken){
        Account account = server.getAccount(authToken);
        List<Message> inbox = account.getInbox();
        StringBuilder out = new StringBuilder();

        for (Message message : inbox)
            out.append(message.getId()).append(". from: ").append(message.getSender()).append((message.isRead() ? "\n" : "*\n"));
        output.println(out);
    }

    /**
     * Authenticates the user and reads a message
     * @param args contains the auth token and the message id
     */
    private void readMessage(String[] args){
        if (args.length != 2)
            output.println("Incorrect number of arguments");
        else if (server.authenticate(Integer.parseInt(args[0])))
            readMessage(Integer.parseInt(args[0]), args[1]);
        else
            output.println("Invalid Auth Token");
    }

    /**
     * Reads a specif message, if that message exists
     * @param authToken of the user
     * @param id of the message to be read
     */
    private void readMessage(int authToken, String id){
        Account account = server.getAccount(authToken);
        Message message = account.readMessage(id);

        if (message == null)
            output.println("Message ID does not exist");
        else
            output.println(message);
    }

    /**
     * Authenticates the user and deletes a message
     * @param args contains the auth token and the message id
     */
    private void deleteMessage(String[] args){
        if (args.length != 2)
            output.println("Incorrect number of arguments");
        else if (server.authenticate(Integer.parseInt(args[0])))
            deleteMessage(Integer.parseInt(args[0]), args[1]);
        else
            output.println("Invalid Auth Token");
    }

    /**
     * a specif message, if that message exists
     * @param authToken of the user
     * @param id of the message to be deleted
     */
    private void deleteMessage(int authToken, String id){
        Account account = server.getAccount(authToken);

        if (!account.deleteMessage(id))
            output.println("Message does not exist");
        else
            output.println("OK");
    }
}
