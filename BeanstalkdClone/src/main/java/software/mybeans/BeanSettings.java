package software.mybeans;

import software.mybeans.commands.*;
import software.mybeans.exceptions.CommandNotFoundException;
import software.mybeans.models.Tube;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeanSettings {
    private static HashMap<String, BeanCommand> commandMap;
    private static BeanCommand unknownCommand;

    public static void loadSettings(){
        loadCommands();
        TubeManager.initializeTubes();
    }

    private static void loadCommands(){
        commandMap = new HashMap<String, BeanCommand>();
        commandMap.put("quit", new QuitCommand());
        commandMap.put("use", new UseCommand());
        commandMap.put("put", new PutCommand());
        unknownCommand = new UnknownCommand();
    }

    public static BeanCommand getCommand(String cmd){
        if(commandMap.containsKey(cmd))
            return commandMap.get(cmd);
        return unknownCommand;
    }


}
