package Application;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private int playerID;
    private Faction faction;
    private int reinforcementNum;
    private ArrayList<Region> regions;
    private Region capital;
    private ArrayList<Card> unusedCards;
    private Mission secretMission;
    private int totalUnitCount;
    private PlayStrategy strategy;
    private HashMap<Player, Boolean> allies;

    public void passAction(){}
    public void attack(int unitCount, Region baseRegion,Region target){}
    public void addReinforcement(int unitCount,Card target){}
    public void addExtraReinforcement(Card card1,Card card2, Card card3){}



}