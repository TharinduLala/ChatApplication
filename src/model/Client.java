package model;

import controller.ClientFormController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    public Client(Socket socket, String userName) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userName = userName;
        } catch (IOException e) {
            e.printStackTrace();
            closeAll(socket,bufferedReader,bufferedWriter);
        }
    }

    public void clientSendMessage(String message) {
        try {
            bufferedWriter.write(userName + " : " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeAll(socket,bufferedReader,bufferedWriter);
        }
    }

    public void clientReceiveMessage(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String message = bufferedReader.readLine();
                        ClientFormController.receivedMessage(message, vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeAll(socket,bufferedReader,bufferedWriter);
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }).start();
    }

    public  void closeAll(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        try {
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
            if (socket!=null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}


