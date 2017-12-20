package software.mybeans;

import software.mybeans.server.ServerThread;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BeanSettings.loadSettings();
        System.out.println("starting server thread!");
        int serverPort = 10000;
        new ServerThread(serverPort);
    }
}
