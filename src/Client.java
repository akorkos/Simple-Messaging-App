import java.util.Arrays;

public class Client {

    /**
     * Represents the client end of the communication
     * @param ip address for the socket stream
     * @param port address for the socket stream
     * @param fid function to be executed
     * @param args necessary information for the function execution
     */
    Client(String ip, String port, String fid, String[] args) {
        RequestHandler requestHandler = new RequestHandler(ip, port);

        requestHandler.openConnection();

        requestHandler.sendRequest(new Request(fid, args));

        requestHandler.closeConnection();
    }
    public static void main(String[] args){
        String ip, port, fid;
        String[] arguments;

        if (args.length < 4) {
            System.err.println("Not enough arguments passed");
            System.exit(1);
        }

        ip = args[0];
        port = args[1];
        fid = args[2];
        arguments = Arrays.copyOfRange(args, 3, args.length);

        new Client(ip, port, fid, arguments);
    }
}
