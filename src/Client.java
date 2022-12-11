import java.io.IOException;
import java.util.Arrays;

public class Client {

    private String ip;
    private String[] args;
    private int port;
    private RequestCodes fid;

    private RequestHandler requestHandler;

    Client(String ip, String port, String fid, String[] args) throws IOException, ClassNotFoundException {
        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.fid = cast(Integer.parseInt(fid));
        this.args = args;

        requestHandler = new RequestHandler(ip, port);

        requestHandler.openConnection();


        Request request = new Request(this.fid, this.args);

        System.out.println("request");

        Response response = requestHandler.sendRequest(request);

        switch (request.getFid()){
            case REGISTER -> System.out.println(response.getAuthToken());
            case EXIT -> requestHandler.closeConnection();
        }

        //
    }

    private RequestCodes cast(int fid){
        return switch (fid) {
            case 1 -> RequestCodes.REGISTER;
            case 2 -> RequestCodes.SHOW_ACCOUNTS;
            case 3 -> RequestCodes.NEW_MESSAGE;
            case 4 -> RequestCodes.SHOW_MESSAGES;
            case 5 -> RequestCodes.READ_MESSAGE;
            case 6 -> RequestCodes.DELETE_MESSAGE;
            case 7 -> RequestCodes.AUTH;
            default -> RequestCodes.INVALID_REQUEST;
        };
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
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
