package managers;

import application.Map;

import java.io.File;
import java.nio.file.Paths;

public class MapManager
{
    public Map getMap(){
        File file = new File("C:\\Users\\scavbob\\java\\1H-RISK\\CS319H_RISK\\resources\\Game\\worldmapdata.txt");
        return new Map(file);
    }
}