package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Match{
    private int round;
    private int maxRound;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private ArrayList<Mission> missionList;
    private HashMap <Faction,Boolean> availableFactions;
    private Map map;
    private GameController controller;

    public Match(){
        this.round = 0;
        this.maxRound = 100;
        this.players = new ArrayList<>();
        this.missionList = new ArrayList<>();
        this.currentPlayer = null;
        this.availableFactions = new HashMap<>();
        this.map = null;
        this.controller = null;
    }

    //constructor
    public Match(int round, int maxRound, ArrayList<Player> players,
                 ArrayList<Mission> missionList,HashMap <Faction,Boolean> availableFactions,
                 Map map, GameController controller){
        this.round = 0;
        this.maxRound = maxRound;
        this.players = players;
        this.missionList = missionList;
        this.currentPlayer = players.get(0);
        this.availableFactions = availableFactions;
        this.map = map;
        this.controller = controller;
    }
    //skips to the next player in line
    public void nextTurn(){
        try {
            if(round < maxRound) {
                round++;
                currentPlayer = players.get(round % players.size());
            }
        } catch(Exception e) {
            System.out.println("Maximum round number has been reached");
        }

    }

    public void attackCommand(int unitCount, Region baseRegion,Region target){
        map.updateRegion(target,unitCount, baseRegion.getOwner());
        update();
    }
    public void update(){
        for(Player p: players ){
            p.update(map);
        }
    }
    public void addPlayer(Player p){
        if (players.size() == 0) currentPlayer = p;
        players.add(p);
    }
    public void setMap(Map map) {
        this.map = map;
    }
    public Map getMap() {
        return map;
    }
}
