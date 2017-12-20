package software.mybeans.commands;

import software.mybeans.models.Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UnknownCommand implements BeanCommand {
    public boolean process(String command, Client client, InputStream in, OutputStream out) throws IOException {
        out.write("UNKNOWN COMMAND\r\n".getBytes());
        return true;
    }
}
