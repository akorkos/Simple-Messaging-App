import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler {
    private int port;
    private ServerSocket serverSocket = null;
    private Socket clientSocket;

    ServerHandler(String port){
        this.port = Integer.getInteger(port);
    }

    public void listen(){
        if (serverSocket != null)
            throw new RuntimeException("Socket already open");

        try {
            serverSocket = new ServerSocket(this.port);

            while (true){
                clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket);
            }
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}