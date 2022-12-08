import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    ClientHandler(Socket socket){
        this.socket = socket;
    }


}
