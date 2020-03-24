package ClientChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection {
    private String Name = null;
    private String serverName;
    private int serverPort;
    private boolean startAsServer = false;
    private boolean startasClient = false;
    private final String END_SESSION_TAG = "/exit";
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;
    //private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    ClientConnection(String serverName, int serverPort) throws IOException {
        this.serverName = serverName;
        this.serverPort = serverPort;
        System.out.println("Type [exit] to close connection and exit.");
    }

    private void setIOStreams (Socket socketName) throws IOException {
        try {
            in = new DataInputStream(socketName.getInputStream());
            out = new DataOutputStream(socketName.getOutputStream());
        } catch (IOException exc){
            exc.printStackTrace();
        }
    }
    // start as server
    public void startAsServer() throws IOException {
        try {
            if (startasClient) throw new IOException();
            this.serverSocket = new ServerSocket(8189);
            System.out.println("Сервер запущен, ожидаем подключения клиента.");
            this.clientSocket = serverSocket.accept();
            System.out.println("Клиент подключен.");
            startAsServer = true;
            setIOStreams(this.clientSocket);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    // start as client
    public void startAsClient (){
        try {
            if (startAsServer) throw new IOException();
            this.clientSocket = new Socket(serverName, serverPort);
            System.out.println("Connected = " + !(clientSocket == null));
            startasClient = true;
            setIOStreams(this.clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read message from connection on inputThread
    public String readMessage () throws IOException {
        String inMessage = null;
        try {
            if (clientSocket.isClosed()){
                System.out.println("Connection is closed!");
                inMessage = "/exit";
            } else inMessage = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inMessage;
    }

    // Send message to connection from outputThread
    public void sendMessages (String outMessage) throws IOException {
        try {
            if (clientSocket.isClosed()) {
                System.out.println("Connection is closed!");
                throw new IOException();
            }
            out.writeUTF(outMessage);
        } catch (IOException exc) {
            throw exc;
        }
    }

    public void setName (String name) {
        this.Name = name;
    }

    public void closeConnection() throws IOException {
        try {
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
            //if (socket != null) socket.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public String getUserName() {
        return this.Name;
    }
}
