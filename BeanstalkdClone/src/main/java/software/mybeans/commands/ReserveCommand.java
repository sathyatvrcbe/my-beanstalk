package software.mybeans.commands;

import software.mybeans.models.Client;
import software.mybeans.models.ReservationRequest;
import software.mybeans.models.Tube;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class ReserveCommand implements BeanCommand {
    public boolean process(String command, Client client, InputStream in, OutputStream out) throws IOException {
        Set<Tube> tubes = client.getWatches();
        ReservationRequest reservationRequest = new ReservationRequest(client);
        for(Tube tube : tubes){
            tube.addReservation(reservationRequest);
        }
        return false;
    }
}
