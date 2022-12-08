import java.io.*;
import java.net.Socket;

public class RequestHandler {
    private String ip;
    private String[] args;
    private int port;
    private RequestCodes fid;
    private Socket socket = null;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    RequestHandler(String ip, String port, RequestCodes fid, String[] args){
        this.ip = ip;
        this.port = Integer.getInteger(port);
        this.fid = fid;
        this.args = args;
    }

    public void openConnection() {
        if (socket != null)
            throw new RuntimeException("Socket already open");
            // System.err.println("Socket already open");
            // System.exit(1);

        try {
            socket = new Socket(ip, port);
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
            // System.err.println(e.getMessage());
            // System.exit(1);
        }
    }

    public Response sendRequest(Request request) {
        try {
            Object response = input.readObject();
            if (response instanceof Response)
                return (Response) response;
            throw new RuntimeException("Bad response");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void closeConnection(){
        try {
            if (socket != null && !socket.isClosed()){
                socket.close();
                input.close();
                output.close();
            }
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }



}
