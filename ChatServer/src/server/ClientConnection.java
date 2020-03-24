package server;

import server.Autentification;
import server.ChatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection {

    private ChatServer server;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private String userName = null;

    ClientConnection(ChatServer chatServer, Socket socket) throws IOException {
        this.clientSocket = socket;
        this.server = chatServer;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public String getUserName() {
        return this.userName;
    }

    public void run (){
        Thread clientThread = new Thread(()-> {
            try {
                if (!AuthNewClient()) {
                    System.out.println("Autentification failed !");
                    return;
                };
                readMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        clientThread.start();
    }

    private void readMessage() throws IOException {
        while (true) {
            String message = in.readUTF();
            System.out.println("New message => " + message);
            String recipient;
            int startIndex = message.indexOf("/w ");
            if (startIndex != -1) {
                startIndex += 3;
                int endIndex = message.indexOf(" ", startIndex);
                recipient = message.substring(startIndex, endIndex);
                System.out.println("recipient = " + recipient);
                message = message.substring(endIndex + 1);
                System.out.println("message = " + message);
                ClientConnection recipientConnection = server.getClient(recipient);
                if (recipientConnection == null) {
                    this.sendMessage("Адресат не подключен к серверу !!!");
                    continue;
                }
                System.out.println("/f " + this.userName +" " + message);
                recipientConnection.sendMessage("/f " + this.userName +" " + message);
            }
            if (message.indexOf("/exit") != -1){
                sendMessage("/exit");
                server.removeClient(this);
                return;
            };
            if (message.indexOf("/getuserlist") != -1){
                sendMessage(new Autentification().getUserListString());
            }
        }
    }

    private void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }

    private boolean AuthNewClient () throws IOException {
        boolean result = false;
        String userName = new Autentification().StartAuthentification(clientSocket);
           if (userName != null) {
               server.addClient(userName, this);
               this.userName = userName;
               result = true;
           };
        return result;
    }
}
