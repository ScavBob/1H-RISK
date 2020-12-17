package application;

import java.io.*;
import java.util.Scanner;

public class Map {
    private int mapID;
    private String mapName;
    private boolean [][] adjacenyMatrix;
    private Region[] regionList;

    public Map(File mapData) {
        initializeRegions(mapData);
    }


    public void printRegions(){
        for( Region i : regionList){
            System.out.println(i.getRegionName() + i.RegionID() + i.getxCoordinate()+i.getyCoordinate());
        }
    }

    //Parse the regions from the
    private void initializeRegions(File mapData){

        Scanner scanner = null;
        try {
            scanner = new Scanner(mapData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int continentCount = scanner.nextInt();
        int regionCount,regionID,regionX,regionY,continentID;
        String regionName,continentName;
        regionList = new Region[42];
        int regionCounter = 0;
        for (int i = 0; i < continentCount; i++){
            continentID = scanner.nextInt();
            regionCount = scanner.nextInt();
            scanner.nextInt();
            continentName = scanner.next();

            for (int j = 0; j < regionCount; j++){
                regionID = scanner.nextInt();
                regionX = scanner.nextInt();
                regionY = scanner.nextInt();
                regionName = scanner.next();
                Region tmpRegion = new Region(regionID,regionName,regionX,regionY);
                System.out.println(regionID + ", " + regionName + ", " + regionX + ", " + regionY);
                System.out.println("j:" + j + ", regionCount:" + regionCount);
                regionList[regionCounter++]= tmpRegion;
            }
        }
    }

    public Region[] getRegionList(){
        return regionList;
    }
}
