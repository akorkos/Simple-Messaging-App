package edu.auth.csd.nco0502.server;

import java.util.ArrayList;

public class Server {

    /**
     * Represents the server side of the communication. Additionally, some
     * accounts with messages are created and stored
     * @param port that the serves is listening
     */
    Server(String port){

        /*
         * Dummy users creation, for testing.
         */
        ArrayList<Account> accounts = new ArrayList<>();

        Account acc1 = new Account("Alex", 1000);
        Account acc2 = new Account("Alkis", 1001);
        Account acc3 = new Account("Panos", 1002);

        acc1.addMessage("Alkis","Test 1");
        acc1.addMessage("Panos","Test 2");
        acc3.addMessage("Alkis", "Test 3");
        acc3.addMessage("Alex","Test 4");

        accounts.add(acc1);
        accounts.add(acc2);
        accounts.add(acc3);

        new ServerHandler(port, accounts);
    }
    public static void main(String[] args){
        String port;

        if (args.length != 1) {
            System.err.println("Not enough or incorrect number of arguments passed");
            System.exit(1);
        }

        port = args[0];

        new Server(port);
    }
}
