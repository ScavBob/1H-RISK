package manager;

import application.Map;
import application.Player;
import application.Region;

import java.io.File;
import java.net.URL;

public class MapManager
{
    public static final int WORLD_MAP = 1;
    public static final int BILKENT_MAP = 2;

    public Map getMap(int map){
        String path = "";
        String name = "";
        if (map == WORLD_MAP) {
            name = "World";
            path = "/GameResources/worldmapdata.txt";
        }
        else if (map == BILKENT_MAP) {
            name = "Bilkent";
            path = "/GameResources/bilkentmapdata.txt";
        }

        return new Map(name, path);
    }

    public void increaseArmyCount(Region region, int increasetAmount)
    {
        region.setUnitCount(region.getUnitCount() + increasetAmount);
    }

    public void updateRegion(Region region, int newArmyCount, Player newOwner)
    {
        //TODO
    }
}