package Lesson06;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import sun.plugin2.message.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MessageProvider {
    private String Name;
    private String serverName;
    private int serverPort;
    private boolean startAsServer = false;
    private boolean startasClient = false;
    private final String END_SESSION_TAG = "[exit]";
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;
    //private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    MessageProvider(String name, String serverName, int serverPort) {
        this.Name = name;
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
    public void readMessages () throws IOException {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (clientSocket.isClosed()) {
                    System.out.println("Connection is closed!");
                    break;
                }
                String inMessage = in.readUTF();
                System.out.println("From " + inMessage);
                System.out.println("Введите сообщение: ");
                if (inMessage.indexOf(END_SESSION_TAG) != -1) {
                    closeConnection();
                    break;
                };
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Send message to connection from outputThread
    public void sendMessages () throws IOException {
        try {
            Scanner outScanner = new Scanner(System.in);
            String outMessage;
            while (!Thread.currentThread().isInterrupted()) {
                if (clientSocket.isClosed()) {
                    System.out.println("Connection is closed!");
                    throw new IOException();
                }
                System.out.println("Ввведите сообщение: ");
                outMessage = outScanner.nextLine();
                outMessage = this.Name + " => " + outMessage;
                if (outMessage.indexOf(END_SESSION_TAG) != -1) {
                    out.writeUTF(this.Name +  " close connection! Good by!" + END_SESSION_TAG);
                    System.out.println(this.Name + " close connection!");
                    closeConnection();
                    break;
                } else {
                    out.writeUTF(outMessage);
                }
            }
        } catch (IOException e) {
            throw e;
        }
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
}
