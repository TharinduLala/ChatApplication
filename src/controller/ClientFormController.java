package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {

    public static String userName;
    public AnchorPane clientWindowAP;
    public ScrollPane scrollPane;
    public VBox mainVBox;
    public Button sendButton;
    public Button cameraButton;
    public TextArea textField;
    private Client client;

    public static void receivedMessage(String message, VBox main) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:   #95a5a6;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.getChildren().add(hBox);
            }
        });
    }

    public static void receivedImage(URL path, VBox main) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        ImageView imageView = new ImageView();
        imageView.setStyle("-fx-padding: 10;-fx-max-width: 350;-fx-max-height: 350;-fx-pref-width: 350;-fx-pref-height: 350;-fx-min-height: 350;-fx-min-width: 350");
        imageView.setFitHeight(350);
        imageView.setFitHeight(350);
        imageView.setPreserveRatio(true);
        Image image = new Image(path.toExternalForm(), 350, 350, false, false);
        imageView.setImage(image);
        hBox.getChildren().add(imageView);
        main.getChildren().add(hBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialize");
        try {
            client = new Client(new Socket("localhost", 9000), userName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });
        client.clientReceiveMessage(mainVBox);
        client.clientSendMessage("");
    }

    public void sendMessageOnAction(ActionEvent actionEvent) {
        String message = textField.getText();
        if (!message.isEmpty()) {
            sendMessage(message);
            textField.clear();
            client.clientSendMessage(message);
            textField.clear();
        }
    }

    public void openCameraOnAction(ActionEvent actionEvent) throws Exception {
        File sendFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg;*.jpeg;*.png;*.gif")
        );
        fileChooser.setTitle("Select Image to send");
        sendFile = fileChooser.showOpenDialog(new Stage());
        if (sendFile != null) {
            URL url = Paths.get(String.valueOf(sendFile)).toUri().toURL();
            sendFile(url);
            BufferedImage bufferedImage = ImageIO.read(new File(sendFile.getPath()));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String type = "";
            int i = sendFile.getName().lastIndexOf(".");
            if (i > 0) {
                type = sendFile.getName().substring(i + 1);
            }
            ImageIO.write(bufferedImage, type, bos);
            byte[] data = bos.toByteArray();
        }
    }

    public void sendMessage(String message) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:  #3498db;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        mainVBox.getChildren().add(hBox);
    }

    public void sendFile(URL url) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        ImageView imageView = new ImageView();
        imageView.setStyle("-fx-padding: 10;-fx-max-width: 350;-fx-max-height: 350;-fx-pref-width: 350;-fx-pref-height: 350;-fx-min-height: 350;-fx-min-width: 350");
        imageView.setFitHeight(350);
        imageView.setFitHeight(350);
        imageView.setPreserveRatio(true);
        Image image = new Image(url.toExternalForm(), 350, 350, false, false);
        imageView.setImage(image);
        hBox.getChildren().add(imageView);
        mainVBox.getChildren().add(hBox);
    }
}
