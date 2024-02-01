package edu.auth.csd.nco0502.server;

import edu.auth.csd.nco0502.server.Account;
import edu.auth.csd.nco0502.server.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerHandler {
    private final ArrayList<Account> accounts;

    /**
     * Handles the server functions that need to be performed. In addition,
     * initializes the socket that the server is listening for requests and 
     * provides the accounts "database"
     * @param port to listen
     * @param accounts "database"
     */
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

    /**
     * Checks if an account with a specific username is already created
     * @param username for the account
     * @return if the username is already in use or not
     */
    public boolean accountExists(String username){
        return accounts.stream().filter(acc -> username.equals(acc.getUsername())).findFirst().orElse(null) != null;
    }

    /**
     * Adds a new account in the "database"
     * @param account to be added
     */
    public void addAccount(Account account){
        accounts.add(account);
    }

    /**
     * Authenticates the user i.e. checks if the token is in use
     * @param authToken of the user
     * @return if the user exists or not
     */
    public boolean authenticate(int authToken){
        return accounts.stream().filter(acc -> authToken == acc.getAuthToken()).findFirst().orElse(null) != null;
    }

    /**
     * @return all registered users
     */

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * Searches for an account with a specific username
     * @param username of the account
     * @return the account or null if it is not found
     */
    public Account getAccount(String username){
        return accounts.stream().filter(acc -> username.equals(acc.getUsername())).findFirst().orElse(null);
    }

    /**
     * Searches for an account with a specific auth token
     * @param authToken of the account
     * @return the account or null if it is not found
     */
    public Account getAccount(int authToken){
        return accounts.stream().filter(acc -> authToken == acc.getAuthToken()).findFirst().orElse(null);
    }
}