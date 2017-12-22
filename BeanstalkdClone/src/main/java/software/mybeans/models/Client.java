package software.mybeans.models;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Client {
    private UUID clientId;
    private Tube tube;
    private Socket clientSocket;
    private Set<Tube> watches;

    public Client(Tube tube, Socket clientSocket){
        clientId = UUID.randomUUID();
        this.tube = tube;
        this.clientSocket = clientSocket;
        watches = new HashSet<Tube>();
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

    public void watch(Tube tube){
        watches.add(tube);
    }

    public Set<Tube> getWatches() {
        return watches;
    }
}
