import java.util.ArrayList;

public class Server {

    Server(String port){
        ArrayList<Account> accounts = new ArrayList<>();

        Account acc1 = new Account("Alex");
        Account acc2 = new Account("Alkis");
        Account acc3 = new Account("Panos");

        System.out.println(acc1.getAuthToken() + ", " + acc2.getAuthToken() + ", " + acc3.getAuthToken());

        acc1.addMessage("Alkis","Tets");
        acc1.addMessage("Panos","test");
        acc3.addMessage("Alkis", "fedd");
        acc3.addMessage("Alex","tyrr");

        accounts.add(acc1);
        accounts.add(acc2);
        accounts.add(acc3);

        new ServerHandler(port, accounts);
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
