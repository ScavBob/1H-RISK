package application;

import game.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player implements Serializable {
    Match currentMatch;
    private static int ids = 1;
    private int playerID;
    //private Faction faction;
    private int reinforcementNum;
    private ArrayList<Region> regions;
    private Region capital;
    private ArrayList<Card> unusedCards;
    //private Mission secretMission;
    private int totalUnitCount;
    private PlayStrategy strategy;
    private ArrayList<Player> allies;
    private String name;
    private String color;
    private int availableReinforcements;


    public Player(String name, String color, boolean isHuman){
        this.name = name;
        this.color = color;
        playerID = ids++;
        regions = new ArrayList<Region>();
        totalUnitCount = 0;
        allies = new ArrayList<Player>();
        totalUnitCount = 40;
        if(isHuman)
            strategy = new Human();
        else
            strategy = new AI();
        availableReinforcements = 0;
    }
    public void addCurrentMatch(Match match){
        currentMatch = match;
    }
    public void addAlly(Player aPlayer){
        allies.add(aPlayer);
    }
    public void addRegion(Region aRegion){
        regions.add(aRegion);
        aRegion.setOwner(this);
        refreshUnitCount();
    }

    public void passAction(){}
    public void attack(int unitCount, Region baseRegion,Region target){
        currentMatch.attackCommand(unitCount, baseRegion, target);
    }
    public void addReinforcement(int unitCount,Card target){
    }
    public void addExtraReinforcement(Card card1,Card card2, Card card3){}
    private void refreshUnitCount(){
        reinforcementNum = regions.size()%3;
    }

    public void update(Map m){
        ArrayList<Region> temp = new ArrayList<>();
        for(Region r: m.getRegionList()){
            if(r.getOwner() == this) {
                temp.add(r);
            }
        }
        if(temp.size() != regions.size())
            regions = temp;
    }
    public List<Region> getRegions(){
        return regions;
    }

    public void getPlayerAction(GameController gameController, int currentPhase)
    {
        this.strategy.getNextAction(gameController, currentPhase);
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getAvailableReinforcements() {
        return availableReinforcements;
    }

    public void decreaseAvailableReinforcements(int decreaseAmount)
    {
        availableReinforcements = Math.max(availableReinforcements - decreaseAmount, 0);
    }

    public void setAvailableReinforcements(int availableReinforcements) {
        this.availableReinforcements = availableReinforcements;
    }

    public int checkForContinentBonuses(){
        int bonusReinforcements = 0;
        boolean rowAllOccupied = true;
        Map map = Game.getInstance().getGameManager().getMatch().getMap();
        int[] continentBonus = map.getContinentBonus();
        int[][] temp;
        temp = new int[map.getMapContinentCount()][];
        //temp initialization

        for(int i = 0; i <map.getMapContinentCount() ; i++){
            temp[i] = new int[map.getContinentRegionNumbers()[i]];
        }
        for(int i = 0; i <map.getMapContinentCount() ; i++){
            for(int j= 0; j < temp[i].length; j++){
                temp[i][j] = -1;
            }
        }

        int regionCounter = 0;
        for(int i = 0; i < map.getMapContinentCount(); i++){

            for (int j = 0; j < regions.size(); j++ ){
                if(temp[i][regionCounter] == -1 && i == regions.get(j).getContinentID() ){
                    temp[i][regionCounter] = regions.get(j).RegionID();
                    regionCounter = regionCounter < temp[i].length -1  ? regionCounter + 1 : regionCounter % temp[i].length;
                }
            }
            regionCounter = 0;
        }
        for(int i = 0; i < map.getMapContinentCount(); i++){
            for (int j = 0; j < temp[i].length; j++ ){
                if(temp[i][j] == -1){
                    rowAllOccupied = false;
                }
            }
            if(rowAllOccupied){
                bonusReinforcements += continentBonus[i];
            }
            rowAllOccupied = true;
        }
        return bonusReinforcements;
    }
}