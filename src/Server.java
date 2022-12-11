import me.nimavat.shortid.ShortId;

import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    private int port;

    private ArrayList<Account> accounts;

    ServerHandler serverHandler;
    Server(String port){
        System.out.println(port);
        this.port = Integer.parseInt(port);
        serverHandler = new ServerHandler(this.port, accounts);

        setData();
    }

    private void setData(){
        Account acc1 = new Account("alex", ShortId.generate());
        Account acc2 = new Account("alkis", ShortId.generate());
        Account acc3 = new Account("panos", ShortId.generate());

        acc1.addMessage("alkis","Tets");
        acc1.addMessage("panos","test");
        acc3.addMessage("alkis", "fedd");
        acc3.addMessage("alex","tyrr");

        accounts.add(acc1);
        accounts.add(acc2);
        accounts.add(acc3);
    }
    public static void main(String[] args){
        String port;

        if (args.length == 0) {
            System.err.println("Not enough arguments passed");
            System.exit(1);
        }
        port = args[0];


        new Server(port);

    }
}
