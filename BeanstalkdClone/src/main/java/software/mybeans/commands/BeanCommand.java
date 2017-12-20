package software.mybeans.commands;

import software.mybeans.models.Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface BeanCommand {
    boolean process(String command, Client client, InputStream in, OutputStream out) throws IOException;
}
