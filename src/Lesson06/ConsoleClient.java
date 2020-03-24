package Lesson06;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient {
    private final static String SERVER_ADDR = "localhost";
    private final static int SERVER_PORT = 8189;

    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;

    public static void openConnection () throws IOException{

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        MessageProvider client = new MessageProvider("Client", "localhost", 8189);
        client.startAsClient();
        Thread inputThread = new Thread(() -> {
            try {
                client.readMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread outputThread = new Thread(() -> {
            try {
                client.sendMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        inputThread.start();
        outputThread.start();
        inputThread.join();
        outputThread.join();
        client.closeConnection();
    }
}
