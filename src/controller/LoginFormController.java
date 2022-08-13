package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public TextField txtUserName;
    public Button btnLogin;
    public AnchorPane loginAP;

    public void loginOnAction(ActionEvent actionEvent) {
        if (!txtUserName.getText().isEmpty()) {
            ClientFormController.userName = txtUserName.getText();
            Stage stage = (Stage) loginAP.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ClientForm.fxml"))));
                stage.setMaximized(false);
                stage.setResizable(false);
                stage.setTitle(txtUserName.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
