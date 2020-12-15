package Application;

public class Region{
    private int regionID;
    private String regionName;
    private int unitCount;
    private Player controller;
    private boolean isCapital;
    private int continent;
    private Hashmap<int, boolean> permission;

    public void setOwner(Player owner);


}