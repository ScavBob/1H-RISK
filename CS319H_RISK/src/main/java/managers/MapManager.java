package managers;

import application.Map;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class MapManager
{
    public Map getMap(){
        URL url = getClass().getResource("/Game/worldmapdata.txt");
        File file = new File(url.getPath());
        return new Map(file);
    }
}