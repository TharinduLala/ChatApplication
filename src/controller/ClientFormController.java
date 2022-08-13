package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    public AnchorPane clientWindowAP;
    public ScrollPane scrollPane;
    public VBox mainVBox;
    public Button sendButton;
    public Button cameraButton;
    public TextArea textField;

    public static  String userName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void sendMessageOnAction(ActionEvent actionEvent) {
        System.out.println(userName);
    }

    public void openCameraOnAction(ActionEvent actionEvent) {
    }
}
