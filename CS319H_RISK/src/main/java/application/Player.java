package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
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


    public Player(String name, String color){
        this.name = name;
        this.color = color;
        playerID = ids++;
        regions = new ArrayList<Region>();
        totalUnitCount = 0;
        allies = new ArrayList<Player>();
        totalUnitCount = 40;
        strategy = new Human();
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
    public void getRegions(){
        System.out.println(playerID + " regions");
        for(Region r: regions){
            System.out.println(r.getOwner().playerID);
        }
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
}