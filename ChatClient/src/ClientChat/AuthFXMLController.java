package ClientChat;

import com.sun.deploy.net.proxy.ProxyUnavailableException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AuthFXMLController {
    private int loginTimeout = 120;
    @FXML
    private Label connecTimeOutLabel;
    @FXML
    private TextField loginText;
    @FXML
    private  PasswordField passwordText;
    @FXML
    private AnchorPane loginPane;

    private ClientConnection clientConnection;
    private Stage loginStage;

    public void init(ClientConnection clientConnection, Stage stage) {
        this.clientConnection = clientConnection;
        this.loginStage = stage;
        loginStage.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Thread timerThread = new Thread (()-> {
                    try {
                        startLoginTimer(connecTimeOutLabel);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                timerThread.start();
            }
        });
    }

    private void startLoginTimer(Label label) throws InterruptedException {
        while (loginTimeout > 0) {
            Thread.currentThread().sleep(1000);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    label.setText("Осталось - " + loginTimeout + " сек.");
                }
            });
            loginTimeout --;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loginStage.close();
            }
        });
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
        System.out.println(Thread.currentThread().getName());
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
