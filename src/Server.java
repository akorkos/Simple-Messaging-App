import java.util.Arrays;

public class Server {
    private int port;
    Server(String port){
        this.port = Integer.getInteger(port);
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
