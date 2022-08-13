package model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Handler implements Runnable {

    public static ArrayList<Handler> handlers = new ArrayList<>();
    public String userName;
    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;

    public Handler(Socket socket) {
        try {
            this.socket = socket;
            this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName = input.readLine();
            handlers.add(this);
            broadcastMessage(userName + "Joined Chat...!");
        } catch (IOException e) {
            closeAll(socket, input, output);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message;
        while (socket.isConnected()) {
            try {
                message = input.readLine();
                broadcastMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                closeAll(socket, input, output);
                break;
            }
        }
    }

    public void broadcastMessage(String sendingMessage) {
        try {
            for (Handler handler : handlers) {
                if (!handler.userName.equals(userName)) {
                    handler.output.write(sendingMessage);
                    handler.output.newLine();
                    handler.output.flush();
                }
            }
        } catch (IOException e) {
            closeAll(socket, input, output);
            e.printStackTrace();
        }
    }

    public void removeClientHandler() {
        handlers.remove(this);
        broadcastMessage(userName + " has left from chat...!");
    }

    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
                System.out.println("A client left from chat");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

