package server;

import com.sun.corba.se.impl.encoding.BufferManagerWrite;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    private final int port;
    private Map<String, ClientConnection> ConnectionList = new HashMap<String, ClientConnection>();

    ChatServer(int port ) {
        this.port = port;
    }

    public void start () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен. Порт: " + port);
            while (true) {
                System.out.println("Ожидание подключения клиента...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключен...");
                registerNewClient (clientSocket);
            }
        } catch (IOException exc) {

        }
    }

    private void registerNewClient(Socket clientSocket) throws IOException {
        ClientConnection newClient = new ClientConnection(this, clientSocket);
        newClient.run();
    }

    public synchronized void addClient (String login, ClientConnection newConnection) {
        ConnectionList.put (login, newConnection);
    }

    public synchronized void removeClient(ClientConnection clientConnection) {
        ConnectionList.remove(clientConnection.getUserName());
    }

    public synchronized ClientConnection getClient(String recipient) {
        return ConnectionList.get(recipient);
    }


}
