import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler {
    private int port;
    private ServerSocket serverSocket = null;
    private Socket clientSocket;
    private Thread clientHandler;

    private ArrayList<Account> accounts;

    ServerHandler(int port, ArrayList<Account> accounts){
        this.port = port;
        this.accounts = accounts;

        listen();
    }

    public void listen(){
        if (serverSocket != null)
            throw new RuntimeException("Socket already open");

        try {
            serverSocket = new ServerSocket(this.port);

            while (true){
                clientSocket = serverSocket.accept();

                clientHandler = new ClientHandler(clientSocket, accounts);

                clientHandler.start();
            }
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}