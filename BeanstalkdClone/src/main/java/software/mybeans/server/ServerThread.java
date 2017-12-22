package software.mybeans.server;

import software.mybeans.TubeManager;
import software.mybeans.models.Client;
import software.mybeans.models.Tube;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {
    private Thread thread;
    private int serverPort;
    public ServerThread(int serverPort){
        this.serverPort = serverPort;
        thread = new Thread(this, "ServerThread");
        thread.start();
    }
    public void run() {
        System.out.println("Server thread started!");
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                Client client = new Client(TubeManager.getTube("default"), clientSocket);
                new ClientHandler(client);
            }
        }catch(Exception e){
            System.out.println("[Error] Exception while starting server socket - "+e.getMessage());
        }
    }
}
