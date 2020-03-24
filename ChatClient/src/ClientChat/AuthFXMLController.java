package ClientChat;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthFXMLController {
    @FXML
    public TextField loginText;
    @FXML
    public PasswordField passwordText;

    private ClientConnection clientConnection;
    private Stage loginStage;

    public void init(ClientConnection clientConnection, Stage stage) {
        this.clientConnection = clientConnection;
        this.loginStage = stage;
    }

    @FXML
    public void loginBtnOnPress(ActionEvent actionEvent) throws IOException {
        if (loginText.getLength() < 1 || passwordText.getLength() < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Авторизация пользователя !");
            alert.setHeaderText("Ошибка авторизации.");
            alert.setContentText("Неварный формат логина или пароля пользователя!");
            alert.showAndWait();
            return;
        }
        clientConnection.sendMessages("login=<" + loginText.getText() + "> password=<" + passwordText.getText() + ">");
        while (true) {
            String inMessage = clientConnection.readMessage();
            if ("/accept".equals(inMessage)) {
                clientConnection.setName(loginText.getText());
                loginStage.close();
                break;
            }
            if ("/denied".equals(inMessage)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Авторизация пользователя !");
                alert.setHeaderText("Ошибка авторизации.");
                alert.setContentText("Пользователь и пароль не зарегистрированы в системе !");
                alert.showAndWait();
                break;
            }
        }
    }

    @FXML
    public void ccancellBtnOnPress(ActionEvent actionEvent) {
        loginStage.close();
    }
}
