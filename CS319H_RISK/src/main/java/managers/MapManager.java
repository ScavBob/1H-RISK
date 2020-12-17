package managers;

import application.Map;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class MapManager
{
    public static final int WORLD_MAP = 1;
    public static final int BILKENT_MAP = 2;

    public Map getMap(int map){
        URL url = null;
        if (map == WORLD_MAP)
            url = getClass().getResource("/Game/worldmapdata.txt");
        else if (map == BILKENT_MAP)
            url = getClass().getResource("/Game/bilkentmapdata.txt");

        if (url == null)
            return null;
        File file = new File(url.getPath());
        return new Map(file);
    }
}