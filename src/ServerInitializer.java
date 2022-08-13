import model.Server;

import java.net.ServerSocket;

public class ServerInitializer {

    public static void main(String[] args) throws Exception {
        System.out.println("Server is start");
        ServerSocket serverSocket = new ServerSocket(9000);
        System.out.println("Server is Running");
        Server server = new Server(serverSocket);
        server.runServer();
    }
}
