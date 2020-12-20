package managers;

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
        URL url = null;
        if (map == WORLD_MAP)
            url = getClass().getResource("/GameResources/worldmapdata.txt");
        else if (map == BILKENT_MAP)
            url = getClass().getResource("/GameResources/bilkentmapdata.txt");

        if (url == null)
            return null;
        File file = new File(url.getPath());
        String mapName = "Bilkent";
        if(map == WORLD_MAP){
            mapName = "World";
        }
        return new Map(mapName, file);
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