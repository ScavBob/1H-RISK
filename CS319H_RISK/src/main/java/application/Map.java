package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Map implements Serializable{
    private int mapID;
    private String mapName;
    private boolean [][] adjacenyMatrix;
    private Region[] regionList;
    private int mapRegionCount;
    private int mapContinentCount;
    private int[] continentBonus;
    private int[] continentRegionNumbers;


    public Map(String mapName, File mapData) {
        this.mapName = mapName;
        initializeRegions(mapData);
    }

    public int getContinetBonus(Player player){
        int continentBonus = 0;
        return continentBonus;
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
        mapContinentCount = continentCount;
        int totalRegionCount = scanner.nextInt();
        mapRegionCount = totalRegionCount;
        int regionCount,regionID,regionX,regionY,continentID;
        String regionName,continentName;
        regionList = new Region[totalRegionCount];
        int regionCounter = 0;
        continentBonus = new int[continentCount];
        continentRegionNumbers = new int[continentCount];

        for (int i = 0; i < continentCount; i++){
            continentID = scanner.nextInt();
            regionCount = scanner.nextInt();
            continentRegionNumbers[i] = regionCount;
            continentBonus[i] = scanner.nextInt();
            continentName = scanner.next();

            for (int j = 0; j < regionCount; j++){
                regionID = scanner.nextInt();
                regionX = scanner.nextInt();
                regionY = scanner.nextInt();
                regionName = scanner.next();
                regionName = regionName.replaceAll("_", " ");
                Region tmpRegion = new Region(regionID,regionName,regionX,regionY, continentID);
                System.out.println(regionID + ", " + regionName + ", " + regionX + ", " + regionY);
                System.out.println("j:" + j + ", regionCount:" + regionCount);
                regionList[regionCounter++]= tmpRegion;
            }
        }

        adjacenyMatrix = new boolean[totalRegionCount][totalRegionCount];
        for (int row = 0; row < totalRegionCount; row++) {
            for (int col = 0; col < totalRegionCount; col++) {
                adjacenyMatrix[row][col] = false;
            }
        }
        while (scanner.hasNextInt()){
            int firstRegion =  scanner.nextInt();
            int secondRegion = scanner.nextInt();
            adjacenyMatrix[firstRegion][secondRegion] = true;
            adjacenyMatrix[secondRegion][firstRegion] = true;
        }

        for(int i = 0; i < continentCount; i++ ){
            System.out.println(continentBonus[i]);
        }
    }

    public boolean isAdjacentRegion(Region r1, Region r2 ){
        return adjacenyMatrix[r1.RegionID()][r2.RegionID()];
    }

    public Region[] getRegionList(){
        return regionList;
    }

    public void updateRegion(Region region, int newUnitCount, Player owner ){
        int location = -1;
        //location = java.util.Arrays.binarySearch(regionList,region);
        for(int i = 0; i < regionList.length;i++){
            if(regionList[i] == region)
                location = i;
        }
        System.out.println("location =" + location);
        regionList[location].setOwner(owner);
        regionList[location].setUnitCount(newUnitCount);

    }

    public String getMapName(){
        return mapName;
    }

    public int[] getContinentBonus(){
        return continentBonus;
    }

    public int getMapRegionCount() {
        return mapRegionCount;
    }

    public int getMapContinentCount(){
        return mapContinentCount;
    }

    public int[] getContinentRegionNumbers(){
        return continentRegionNumbers;
    }


    public ArrayList<Region> getNeighbourOf(Region r){
        ArrayList<Region> list = new ArrayList<Region>();
        for(int i = 0; i< adjacenyMatrix[r.RegionID()].length;i++){
            if(adjacenyMatrix[r.RegionID()][i] )
                list.add(findByID(i));
        }
        return list;
    }

    private Region findByID(int id){
        for(int i = 0;i < regionList.length;i++){
            if(regionList[i].RegionID() == id)
                return regionList[i];
        }
        return null;
    }
}
