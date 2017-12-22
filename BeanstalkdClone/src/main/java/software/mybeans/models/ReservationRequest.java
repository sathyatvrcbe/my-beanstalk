package software.mybeans.models;

public class ReservationRequest {
    private Client client;
    private boolean done;

    public ReservationRequest(Client client) {
        this.client = client;
        this.done = false;
    }

    public boolean isDone() {
        return done;
    }

    public Client getClient() {
        return client;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


}
