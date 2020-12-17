package application;

public class Map {
    private int mapID;
    private String mapName;
    private boolean [][] adjacenyMatrix;
    private Region[] regionList;
    private int [][] continentList;

    public Map(int mapID,String mapName,boolean [][] adjacenyMatrix,Region[] regionList,int [][] continentList){
        this.mapID = mapID;
        this.mapName = mapName;
        this.adjacenyMatrix = adjacenyMatrix;
        this.regionList = regionList;
        this.continentList = continentList;
    }
    public Map(){
        Card[] regionList = new Card[6];
        initializeRegions();
        //Talked with the visual part of the group.
        //adjacenyMatrix = new int[7][7];
    }

    private void initializeRegions(){
        // what does it mean type in the attributes of the card class?
        Region  tmpRegion;
        tmpRegion =  new Region(1,"Australia"); regionList[0] = tmpRegion;
        tmpRegion = new Region(2,"North America");regionList[1] = tmpRegion;
        tmpRegion = new Region(3,"South America");regionList[2] = tmpRegion;
        tmpRegion = new Region(4,"Africa");regionList[3] = tmpRegion;
        tmpRegion = new Region(5,"Europe");regionList[4] = tmpRegion;
        tmpRegion = new Region(6,"Asia");regionList[5] = tmpRegion;
    }
    public Region[] getRegionList(){
        return regionList;
    }
}
