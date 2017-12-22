package software.mybeans.server;

import software.mybeans.BeanSettings;
import software.mybeans.models.Client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ClientHandler implements Runnable {
    private Thread th;
    private Client client;
    public ClientHandler(Client client){
        this.client = client;
        th = new Thread(this,"ClientHandler");
        th.start();
    }

    public void run() {
        try {

            System.out.println("Started client - "+client.getClientId());
            InputStream in = client.getClientSocket().getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String command = "";
            while(true){
                int x = in.read();
                if(x==-1) break;
                char cc =  ((char)x);
                command += cc;
                if(cc == '\n'){
                    System.out.println("currentCommand - "+command);
                    boolean exit = process(command, in, client.getClientSocket().getOutputStream());
                    command = "";
                    if(exit) break;
                }
            }
            System.out.println(command);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("[Error] Error while handling client "+client.getClientId());
        }
        System.out.println("Finished serving client - "+client.getClientId());
    }

    private boolean process(String command, InputStream in, OutputStream out) throws Exception{
        String[] commandWithArgs = command.split(" ");
        String cmd = commandWithArgs[0].trim().toLowerCase();
        return BeanSettings.getCommand(cmd).process(command,client,in,out);
    }
}
