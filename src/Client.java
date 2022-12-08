import java.util.Arrays;

public class Client {
    // final private String REGEX = "[a-zA-Z0-9-]";
    private String ip;
    private String[] args;
    private int port;
    private RequestCodes fid;

    private RequestHandler requestHandler;

    Client(String ip, String port, String fid, String[] args){
        this.ip = ip;
        this.port = Integer.getInteger(port);
        this.fid = cast(Integer.parseInt(fid));
        this.args = args;
        requestHandler = new RequestHandler(ip, port, this.fid, args);

        execute();
    }

    private void execute(){
        requestHandler.openConnection();

        requestHandler.sendRequest(new Request(this.fid, this.args));

        requestHandler.closeConnection();
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
