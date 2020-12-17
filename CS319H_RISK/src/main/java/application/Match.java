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
}
