package software.mybeans.commands;

import software.mybeans.TubeManager;
import software.mybeans.models.Client;
import software.mybeans.models.Tube;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UseCommand implements BeanCommand {
    public boolean process(String command, Client client, InputStream in, OutputStream out) throws IOException {
        String[] cmds = command.split(" ");
        Tube tube;
        if(cmds.length==1) {
            tube = TubeManager.getTube("default");
        }else{
            tube = TubeManager.getTube(cmds[1].trim());
        }
        client.setTube(tube);
        out.write(("USING "+client.getTube().getTubeName()+"\r\n").getBytes());
        return false;
    }
}
