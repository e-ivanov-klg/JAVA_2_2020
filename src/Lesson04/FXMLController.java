package Lesson04;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;

public class FXMLController {
    /*
    FXMLController (){

    }
    */
    @FXML
    private Button btn1;
    @FXML
    private ListView usersListView;
    @FXML
    private ListView dialogList;
    @FXML
    private TextField messageTextBox;

    private  ObservableList<String> userList = FXCollections.observableArrayList("Ivanov", "Petrov", "Sidorov");
    private ObservableList<String> messageList = FXCollections.observableArrayList();

    public void initUsersList() {
        usersListView.setItems(userList);


    }
    @FXML
    private void onSendButtonClick(ActionEvent actionEvent) {
        int selectedIndex = usersListView.getFocusModel().getFocusedIndex();
        if ( selectedIndex == -1) { return; };
        if (messageTextBox.getText().length() == 0) { return; }
        messageList.add("Вы: " + messageTextBox.getText());
        //messageList.add(usersListView.getItems().get(selectedIndex) + ": Hello JavaFX!");
        messageList.add(usersListView.getFocusModel().getFocusedItem().toString() +  ": Hello JavaFX!");
        dialogList.setItems(messageList);

    }
    @FXML
    private void messageTextBoxKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onSendButtonClick(new ActionEvent());
        }
    }
}
