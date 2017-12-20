package software.mybeans.commands;

import software.mybeans.models.Client;
import software.mybeans.models.Tube;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PutCommand implements BeanCommand {
    public boolean process(String command, Client client, InputStream in, OutputStream out) throws IOException {
        Tube tube = client.getTube();
        String[] cmd = command.split(" ");

        return false;
    }
}
