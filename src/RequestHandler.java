import java.io.*;
import java.net.Socket;

public class RequestHandler {
    private String ip;

    private int port;

    private Socket socket = null;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    RequestHandler(String ip, String port){
        this.ip = ip;
        this.port = Integer.parseInt(port);
    }

    public void openConnection() throws IOException {

        if (socket != null) {
            //throw new RuntimeException("Socket already open");
            System.err.println("Socket already open");
            System.exit(1);
        }

        socket = new Socket(ip, port);
        System.out.println(socket.isConnected());
        input = new ObjectInputStream(socket.getInputStream());
        System.out.println(input.available());
        output = new ObjectOutputStream(socket.getOutputStream());
    }

    public Response sendRequest(Request request) throws IOException, ClassNotFoundException {
        output.writeObject(request);

        Object response = input.readObject();

        if (response instanceof Response)
            return (Response) response;
        throw new RuntimeException("Bad response");
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
