package application;

public class Map {
    private int mapID;
    private String mapName;
    private int [][] adjacenyMatrix;
    private Card[] regionList;
    private int [][] continentList;

    public Map(int mapID,String mapName,int [][] adjacenyMatrix,Card[] regionList,int [][] continentList){
        this.mapID = mapID;
        this.mapName = mapName;
        this.adjacenyMatrix = adjacenyMatrix;
        this.regionList = regionList;
        this.continentList = continentList;
    }
    public Map(){
        Card regionList = new Card[6];
        initializeRegions();
        adjacenyMatrix = new int[7][7];             // Umut'a sor. 
    }

    private void initializeRegions(){
        // what does it mean type in the attributes of the card class?
        card = new Card(new Region(1,"Australia"),0);regionList[0] = card;
        card = new Card(new Region(2,"North America"),0);regionList[1] = card;
        card = new Card(new Region(3,"South America"),0);regionList[2] = card;
        card = new Card(new Region(4,"Africa"),0);regionList[3] = card;
        card = new Card(new Region(5,"Europe"),0);regionList[4] = card;
        card = new Card(new Region(6,"Asia"),0);regionList[5] = card;
    }
}
