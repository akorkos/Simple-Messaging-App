import me.nimavat.shortid.ShortId;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler extends Thread{

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private ArrayList<Account> accounts;


    ClientHandler(Socket socket, ArrayList<Account> accounts){
        this.socket = socket;
        this.accounts = accounts;
        try {
            input = new ObjectInputStream(this.socket.getInputStream());
            output = new ObjectOutputStream(this.socket.getOutputStream());

            respond();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void respond(){
        try{
            while (true) {
                Request request;
                if (input.readObject() instanceof Request) {
                    request = (Request) input.readObject();

                    Account user = auth(request.getArg(0), request.getArg(1));

                    if (user == null)
                        output.writeObject(new Response(ResponseCodes.NO_AUTH));
                    else {
                        switch (request.getFid()) {
                            case REGISTER -> output.writeObject(createAccount(request.getArg(0)));
                            case NEW_MESSAGE -> output.writeObject(sendMessage(user, request.getArg(0), request.getArg(1)));
                            case READ_MESSAGE -> output.writeObject(readMessage(user, request.getArg(0)));
                            case DELETE_MESSAGE -> output.writeObject(deleteMessage(user, request.getArg(0)));
                            case SHOW_MESSAGES -> output.writeObject(showInbox(user));
                            case SHOW_ACCOUNTS -> output.writeObject(showAccounts());
                        }
                    }
                } else
                    output.writeObject(new Response(ResponseCodes.BAD_REQUEST));
            }
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private Account auth(String username, String authToken){
        Account user = accounts.stream().filter(acc -> username.equals(acc.getUsername())).findFirst().orElse(null);

        if (user != null && Objects.equals(user.getAuthToken(), authToken))
            return user;
        return null;
    }

    private Response createAccount(String username){
        Pattern pattern = Pattern.compile("[A-Za-z-]");
        Matcher matcher = pattern.matcher(username);

        Account recipient = accounts.stream().filter(acc -> username.equals(acc.getUsername())).findFirst().orElse(null);



        if (!matcher.matches())
            return new Response(ResponseCodes.BAD_USERNAME);

        if (recipient == null)
            return new Response(ResponseCodes.USER_EXISTS);

        String authToken = ShortId.generate();

        accounts.add(new Account(username, authToken));

        return new Response(ResponseCodes.OK, authToken);
    }

    private Response showAccounts(){
        return new Response(ResponseCodes.OK, accounts);
    }

    private Response sendMessage(Account sender, String recipientUsername, String message){
        Account recipient = accounts.stream().filter(acc -> recipientUsername.equals(acc.getUsername())).findFirst().orElse(null);

        if (recipient == null)
            return new Response(ResponseCodes.INVALID_RECIPIENT);

        recipient.addMessage(sender.getUsername(), message);

        return new Response(ResponseCodes.OK);
    }

    private Response showInbox(Account user){
        return new Response(ResponseCodes.OK, user.getInbox());
    }

    private Response readMessage(Account user, String id){
        Message message = user.readMessage(id);

        if (message == null)
            return new Response(ResponseCodes.BAD_ID);

        return new Response(ResponseCodes.OK, message);
    }

    private Response deleteMessage(Account user, String id){
        boolean success = user.deleteMessage(id);
        if (success)
            return new Response(ResponseCodes.OK);
        return new Response(ResponseCodes.BAD_ID);
    }
}
