import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerHandler {
    private final ArrayList<Account> accounts;

    ServerHandler(String port, ArrayList<Account> accounts){
        this.accounts = accounts;

        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port));

            while (true)
                new ClientHandler(serverSocket.accept(), this).start();

        } catch (IOException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public boolean accountExists(String username){
        return accounts.stream().filter(acc -> username.equals(acc.getUsername())).findFirst().orElse(null) != null;
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public boolean authenticate(int authToken){
        return accounts.stream().filter(acc -> authToken == acc.getAuthToken()).findFirst().orElse(null) != null;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(String username){
        return accounts.stream().filter(acc -> username.equals(acc.getUsername())).findFirst().orElse(null);
    }

    public Account getAccount(int authToken){
        return accounts.stream().filter(acc -> authToken == acc.getAuthToken()).findFirst().orElse(null);
    }
}