package ClientChat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FXMLController {

    private ClientConnection clientConnection;

    @FXML
    private Button btn1;
    @FXML
    private ListView usersListView;
    @FXML
    private ListView dialogListView;
    @FXML
    private TextField messageTextBox;
    @FXML
    private Label userNameLabel;

    private  ObservableList<String> userList = FXCollections.observableArrayList();
    private Map<String, ObservableList<String>> userMessageList = new HashMap<>();

    @FXML
    private void onSendButtonClick(ActionEvent actionEvent) throws IOException {
        int selectedIndex = usersListView.getFocusModel().getFocusedIndex();
        if ( selectedIndex == -1) { return; };
        if (messageTextBox.getText().length() == 0) { return; }
        String recipient = usersListView.getItems().get(selectedIndex).toString();
        updateDialogList(recipient, clientConnection.getUserName(), messageTextBox.getText());
        clientConnection.sendMessages("/w "+ recipient + " "  + messageTextBox.getText());
    }
    @FXML
    private void messageTextBoxKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onSendButtonClick(new ActionEvent());
        }
    }

    public void start(ClientConnection clConnection, Stage mainStage) throws IOException, InterruptedException {
        // Create and show login dialog
        Stage loginStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AuthForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        loginStage.setScene(scene);
        loginStage.setTitle("Авторизация в чат.");
        AuthFXMLController controller = loader.getController();
        this.clientConnection = clConnection;
        controller.init(clientConnection, loginStage);
        loginStage.initModality(Modality.APPLICATION_MODAL);
        System.out.println(Thread.currentThread().getName());
        loginStage.showAndWait();
        if (clientConnection.getUserName() == null) {
            mainStage.hide();
            return;
        }
        System.out.println("AfterLogin");
        userNameLabel.setText(clientConnection.getUserName());
        clientConnection.sendMessages("/getuserlist");
        Thread inThread = new Thread(()-> {
            try {
                readMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        inThread.start();
    }

    private void readMessages() throws IOException {
        int startIndex = -1;
        int endIndex = -1;
        while (true){
            String newMessage = clientConnection.readMessage();
            System.out.println(newMessage);
            if ("/exit".equals(newMessage)) return;
            // служебная команда пересылки списка пользователей
            if (newMessage.indexOf("/userlist=") != -1) createUserList (newMessage);
            // входящие сообщения от адресата
            startIndex = newMessage.indexOf("/f ");
            if (startIndex != -1) {
                startIndex +=3;
                endIndex = newMessage.indexOf(" ", startIndex);
                String fromUser = newMessage.substring(startIndex, endIndex);
                startIndex = startIndex + fromUser.length();
                newMessage = newMessage.substring(startIndex);
                updateDialogList (fromUser, fromUser, newMessage);
            }
            startIndex = -1;
            endIndex = -1;
        }
    }

    private void updateDialogList(String recipient, String sender, String newMessage) {
        userMessageList.get(recipient).add(sender + ": " + newMessage);
    }

    private void createUserList(String newMessage) {
        int startIndex = -1;
        int endIndex = -1;
        String newUser = null;
        usersListView.setItems(null);
        startIndex = newMessage.indexOf("/userlist=") + 10;
        while (true) {
            endIndex = newMessage.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = newMessage.length();
            }
            newUser = newMessage.substring(startIndex, endIndex);
            System.out.println(newUser);
            if (!clientConnection.getUserName().equals(newUser)) {
                userList.add(newUser);
                userMessageList.put(newUser, FXCollections.observableArrayList());
            }
            startIndex = endIndex +1;
            if (startIndex > newMessage.length()) break;
        }
        usersListView.setItems(userList);
    }

    public void onMouseClick(MouseEvent mouseEvent) {
        int selectedIndex = usersListView.getFocusModel().getFocusedIndex();
        if ( selectedIndex == -1) { return; };
        String userName = usersListView.getItems().get(selectedIndex).toString();
        dialogListView.setItems(userMessageList.get(userName));
    }
}
