package edu.auth.csd.nco0502.client;

import edu.auth.csd.nco0502.common.Request;

import java.io.*;
import java.net.Socket;

public class RequestHandler {
    private final String ip;
    private final int port;
    private Socket socket = null;
    private BufferedReader input;
    private ObjectOutputStream output;

    /**
     * Creates a handler, for sending requests and for receiving the server responses.
     * Additionally, opens the communication socket stream
     * @param ip address for the socket
     * @param port address for the socket
     */
    RequestHandler(String ip, String port){
        this.ip = ip;
        this.port = Integer.parseInt(port);
    }

    /**
     * Initializes the socket and the streams for sending and receiving information
     */
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

    /**
     * Sending a request to the server and receiving and printing the server response
     * @param request that the user wants to execute
     */
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

    /**
     * Closes the input, output and the socket
     */
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
