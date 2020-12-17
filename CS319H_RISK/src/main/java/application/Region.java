package application;

import java.util.HashMap;

public class Region{
    private int regionID;
    private String regionName;
    private int unitCount;
    private Player controller;
    private boolean isCapital;
    private int continent;
    private int xCoordinate;
    private int yCoordinate;

    private HashMap<Integer, Boolean> permission;

    public Region(int regionID, String regionName,int xCoordinate,int yCoordinate) {
        this.regionID = regionID;
        this.regionName = regionName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
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
    public void setOwner(Player owner){}
    public Player getController(){return controller;}

}