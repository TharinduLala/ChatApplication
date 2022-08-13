package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Server is start");
        ServerSocket serverSocket = new ServerSocket(9000);
        System.out.println("Server is Running");
        Server server = new Server(serverSocket);
        server.runServer();
    }

    public void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void runServer() {
        while (!(serverSocket.isClosed())) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("new Client Connected");
                Thread thread = new Thread(new Handler(socket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
                closeServer();
            }
        }
    }

}

