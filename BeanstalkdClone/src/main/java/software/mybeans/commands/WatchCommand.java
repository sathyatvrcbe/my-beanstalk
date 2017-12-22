package software.mybeans.commands;

import software.mybeans.TubeManager;
import software.mybeans.models.Client;
import software.mybeans.models.Tube;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WatchCommand implements BeanCommand{
    public boolean process(String command, Client client, InputStream in, OutputStream out) throws IOException {
        String tubeName = command.split(" ")[1].trim();
        Tube tube = TubeManager.getTube(tubeName);
        client.watch(tube);
        out.write(("WATCHING "+tubeName+"\r\n").getBytes());
        System.out.println("Watching "+tubeName);
        return false;
    }
}
