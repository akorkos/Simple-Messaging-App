import java.io.*;
import java.net.Socket;

public class RequestHandler {
    private final String ip;

    private final int port;

    private Socket socket = null;

    private BufferedReader input;
    private ObjectOutputStream output;

    RequestHandler(String ip, String port){
        this.ip = ip;
        this.port = Integer.parseInt(port);
    }

    public void openConnection(){
        if (socket != null) {
            System.err.println("Socket already open");
            System.exit(1);
        }

        try {
            socket = new Socket(ip, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void sendRequest(Request request) {
        try {
            output.writeObject(request);
            String out;
            while ((out = input.readLine()) != null)
                System.out.println(out);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void closeConnection(){
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
