import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler extends Thread{

    private final Socket socket;
    private final ServerHandler server;
    private ObjectInputStream input;
    private PrintWriter output;

    ClientHandler(Socket socket, ServerHandler server){
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
    private boolean isValid(String username){
        Pattern usernamePattern = Pattern.compile("^([a-zA-Z])+([\\w]{2,})+$");
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.matches();
    }

    private void createAccount(String[] args){
        createAccount(args[0]);
    }
    private void createAccount(String username){
        if (!isValid(username))
            output.println("Invalid Username");
        else if (server.accountExists(username))
            output.println("Sorry, the user already exists");
        else {
            Account account = new Account(username);
            server.addAccount(account);
            output.println(account.getAuthToken());
        }
    }

    private void showAccounts(String[] args){
        if (server.authenticate(Integer.parseInt(args[0])))
            showAccounts();
    }

    private void showAccounts(){
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < server.getAccounts().size(); i++)
            out.append(i + 1).append(". ").append(server.getAccounts().get(i)).append("\n");
        output.println(out);
    }

    private void sendMessage(String[] args){
        if (server.authenticate(Integer.parseInt(args[0])))
            sendMessage(args[1], args[2]);
    }

    private void sendMessage(String recipient, String message){
        Account recipientAccount = server.getAccount(recipient);

        if (recipientAccount == null)
            output.println("User does not exist");
        else {
            recipientAccount.addMessage(recipient, message);
            output.println("OK");
        }
    }

    private void showInbox(String[] args){
        if (server.authenticate(Integer.parseInt(args[0])))
            showInbox(Integer.parseInt(args[0]));
    }

    private void showInbox(int authToken){
        Account account = server.getAccount(authToken);
        List<Message> inbox = account.getInbox();
        StringBuilder out = new StringBuilder();

        for (Message message : inbox)
            out.append(message.getId()).append(". from: ").append(message.getSender()).append(message.isRead() ? "\n" : "*\n");
        output.println(out);
    }

    private void readMessage(String[] args){
        if (server.authenticate(Integer.parseInt(args[0])))
            readMessage(Integer.parseInt(args[0]), args[1]);
    }

    private void readMessage(int authToken, String id){
        Account account = server.getAccount(authToken);
        Message message = account.readMessage(id);

        if (message == null)
            output.println("Message ID does not exist");
        else
            output.println(message);
    }

    private void deleteMessage(String[] args){
        if (server.authenticate(Integer.parseInt(args[0])))
            deleteMessage(Integer.parseInt(args[0]), args[1]);
    }

    private void deleteMessage(int authToken, String id){
        Account account = server.getAccount(authToken);

        if (!account.deleteMessage(id))
            output.println("Message does not exist");
        else
            output.println("OK");
    }
}
