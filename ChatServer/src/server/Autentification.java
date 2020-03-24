package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Autentification {
    private Map<String, String> users = new HashMap<String, String>();
    private DataInputStream in;
    private DataOutputStream out;

    Autentification () {
        users.put("ivanov", "123");
        users.put("petrov", "123");
        users.put("sidorov", "123");
    }

    public String StartAuthentification(Socket clientSocket) throws IOException {
        String message = null   ;
        String login = null;
        String password = null;
        int startIndex;
        int endIndex;
        String result = null;
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
        while (true){
            message = in.readUTF();
            System.out.println(message);
            if (message.indexOf("cancell") != -1) break;
            startIndex = message.indexOf("login=<") + 7;
            endIndex = message.indexOf(">", startIndex);
            if (startIndex != -1 && endIndex != -1)login = message.substring(startIndex,endIndex);
            if (login == null){
                sendMessage("/deny");
                continue;
            }
            startIndex = message.indexOf("password=<") + 10;
            endIndex = message.indexOf(">", startIndex);
            if (startIndex != -1 && endIndex != -1) password = message.substring(startIndex,endIndex);
            if (password == null) {
                sendMessage("/deny");
                continue;
            }
//            System.out.println(login);
//            System.out.println(password);
//            System.out.println(users.get(login));
            if (users.get(login).equals(password)) {
                result = login;
                sendMessage("/accept");
                break;
            }

        }
        return result;
        //return (users.get(login) == password);
    }

    private void sendMessage(String outMessage) throws IOException {
        out.writeUTF(outMessage);
    }

    public String getUserListString() {
        String userList = "/userlist=";
        for (String userName : users.keySet()) {
            userList = userList + userName + ",";
        }
        return userList.substring(0, userList.length() -1);
    }
}
