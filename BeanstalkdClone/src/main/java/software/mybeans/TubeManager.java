package software.mybeans;

import software.mybeans.models.Tube;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TubeManager {
    private static Set<Tube> tubes;
    private static Map<String,Tube> tubeByName;

    static void initializeTubes(){
        tubes = new HashSet<Tube>();
        synchronized (tubes) {
            tubeByName = new HashMap<String, Tube>();
            Tube defaultTube = new Tube();
            tubes.add(defaultTube);
            tubeByName.put("default",defaultTube);
        }
    }

    public static Tube getTube(String tubeName){
        synchronized (tubes){
            if(tubeByName.containsKey(tubeName)){
                return tubeByName.get(tubeName);
            }else{
                Tube tube = new Tube(tubeName);
                tubes.add(tube);
                tubeByName.put(tubeName,tube);
                return tube;
            }
        }
    }
}
