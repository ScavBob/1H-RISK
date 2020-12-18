package application;

import java.io.Serializable;
import java.util.HashMap;

public class Region implements Serializable {
    private int regionID;
    private String regionName;
    private int unitCount;
    private Player controller;
    private boolean isCapital;
    private int continentID;
    private int xCoordinate;
    private int yCoordinate;

    private HashMap<Integer, Boolean> permission;

    public Region(int regionID, String regionName,int xCoordinate,int yCoordinate, int contID) {
        this.regionID = regionID;
        this.regionName = regionName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.continentID = contID;
    }
    public String getRegionName(){
        return regionName;
    }
    public int RegionID(){
        return regionID;
    }
    public int getxCoordinate(){return xCoordinate;}
    public int getyCoordinate(){return yCoordinate;}
    public int getUnitCount(){return unitCount;}
    public Player getController(){return controller;}

    public void setOwner(Player owner){
        controller = owner;
    }
    public void setUnitCount(int newUnitCount){
        unitCount = newUnitCount;
    }
    public Player getOwner(){
        return controller;
    }
}