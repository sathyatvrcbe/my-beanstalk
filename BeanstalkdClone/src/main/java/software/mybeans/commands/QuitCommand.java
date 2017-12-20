package software.mybeans.commands;

import software.mybeans.models.Client;

import java.io.InputStream;
import java.io.OutputStream;

public class QuitCommand implements BeanCommand {
    public boolean process(String command, Client client, InputStream in, OutputStream out) {
        return true;
    }
}
