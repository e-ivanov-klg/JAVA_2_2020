package Lesson06;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleServer {

    // main
    public static void main(String[] args) throws InterruptedException, IOException {
        MessageProvider client = new MessageProvider("Server", "localhost", 8189);
        client.startAsServer();
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
