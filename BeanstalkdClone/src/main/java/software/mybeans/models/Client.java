package software.mybeans.models;

import java.net.Socket;
import java.util.UUID;

public class Client {
    private UUID clientId;
    private Tube tube;
    private Socket clientSocket;

    public Client(Tube tube, Socket clientSocket){
        clientId = UUID.randomUUID();
        this.tube = tube;
        this.clientSocket = clientSocket;
    }

    public UUID getClientId() {
        return clientId;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public synchronized void setTube(Tube tube){
        this.tube = tube;
    }

    public synchronized Tube getTube() {
        return tube;
    }
}
