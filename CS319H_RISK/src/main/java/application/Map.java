package application;

import java.io.*;
import java.util.Scanner;

public class Map {
    private int mapID;
    private String mapName;
    private boolean [][] adjacenyMatrix;
    private Region[] regionList;

    public Map(String mapDataPath) {
        initializeRegions(mapDataPath);
    }


    public void printRegions(){
        for( Region i : regionList){
            System.out.println(i.getRegionName() + i.RegionID() + i.getxCoordinate()+i.getyCoordinate());
        }
    }

    //Parse the regions from the
    private void initializeRegions(String mapDataPath){

        Scanner scanner = new Scanner(mapDataPath);
        int continentCount = scanner.nextInt();
        int regionCount,regionID,regionX,regionY,continentID;
        String regionName,continentName;
        regionList = new Region[42];
        int regionCounter = 0;
        for (int i = 0; i < continentCount; i++){
            continentID = scanner.nextInt();
            regionCount = scanner.nextInt();
            continentName = scanner.next();

            for (int j = 0; j < regionCount; j++){
                regionID = scanner.nextInt();
                regionX = scanner.nextInt();
                regionY = scanner.nextInt();
                regionName = scanner.next();
                Region tmpRegion = new Region(regionID,regionName,regionX,regionY);
                regionList[regionCounter++]= tmpRegion;
            }
        }
    }

    public Region[] getRegionList(){
        return regionList;
    }
}
