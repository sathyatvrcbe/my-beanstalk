package software.mybeans;

import software.mybeans.commands.*;
import software.mybeans.exceptions.CommandNotFoundException;
import software.mybeans.models.ReservationQueue;
import software.mybeans.models.Tube;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeanSettings {
    private static HashMap<String, BeanCommand> commandMap;
    private static BeanCommand unknownCommand;
    private static ReservationQueue reservationQueue;

    public static void loadSettings(){
        loadCommands();
        JobManager.initialize();
        reservationQueue = new ReservationQueue();
        TubeManager.initializeTubes();
    }

    private static void loadCommands(){
        commandMap = new HashMap<String, BeanCommand>();
        commandMap.put("quit", new QuitCommand());
        commandMap.put("use", new UseCommand());
        commandMap.put("put", new PutCommand());
        commandMap.put("watch", new WatchCommand());
        commandMap.put("reserve", new ReserveCommand());
        //commandMap.put("reserve-with-timeout", new ReserveCommand());
        commandMap.put("delete", new DeleteCommand());
        unknownCommand = new UnknownCommand();
    }

    public static BeanCommand getCommand(String cmd){
        if(commandMap.containsKey(cmd))
            return commandMap.get(cmd);
        return unknownCommand;
    }

    public static ReservationQueue getReservationQueue() {
        return reservationQueue;
    }
}
