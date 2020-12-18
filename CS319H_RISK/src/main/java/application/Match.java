package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Match {
    private int round;
    private int maxRound;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private ArrayList<Mission> missionList;
    private HashMap<Faction, Boolean> availableFactions;
    private Map map;
    private GameController controller;

    //constructor
    public Match(){
        this.round = 0;
        this.maxRound = 100;
        this.players = new ArrayList<>();
        this.missionList = new ArrayList<>();
        this.currentPlayer = null;
        this.availableFactions = new HashMap<>();
        this.map = null;
        this.controller = new GameController(this);
    }


    public Match(int round, int maxRound, ArrayList<Player> players,
                 ArrayList<Mission> missionList, HashMap<Faction, Boolean> availableFactions,
                 Map map, GameController controller) {
        this.round = 0;
        this.maxRound = maxRound;
        this.players = players;
        this.missionList = missionList;
        this.currentPlayer = players.get(0);
        this.availableFactions = availableFactions;
        this.map = map;
        this.controller = controller;
    }

    //isMatcOver method returns true if match is over, false if match is continues
    public boolean isMatchOver(){
           boolean isOver = false;
           int alivePlayerCount = 0;
           for (int i = 0; i < players.size(); i++ ){
               if (isPlayerAlive(players.get(i)) == true ) {
                    alivePlayerCount +=  1;
               }
           }
           isOver = (alivePlayerCount == 1) ? true : false;
        return isOver;
    }

    //skips to the next player in line
    public void nextTurn() {
        //TODO
        //Skip dead (?) players.
        if (round < maxRound) {
            round++;
            currentPlayer = players.get(round % players.size());
        }
        giveInitialReinforcement(currentPlayer);
    }

    public void giveInitialReinforcement(Player player)
    {
        int reinforcementsFromRegions = player.getRegions().size() / 3;

        //TODO
        int reinforcementsFromContinents = 0;

        player.setAvailableReinforcements(reinforcementsFromRegions + reinforcementsFromContinents);
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    private boolean isPlayerAlive(Player player)
    {
        return player.getRegions().size() != 0;
    }

    public void initialize(){

        boolean[] available = new boolean[map.getRegionList().length];

        int lng = available.length;

        for(int i = 0; i < lng;i++){
            available[i] = true;
        }

        for (int i = 0; i < lng; i++){
            int loc;
            do {
                loc = (int) (Math.random() * lng);
            }while(!available[loc]);

            available[loc] = false;

            players.get(i % players.size()).addRegion(map.getRegionList()[loc]);

            map.getRegionList()[loc].setUnitCount((int)(Math.random()*10 + 1));
        }

        //The first player cannot get the initial reinforcements from nextTurn() method, so we give it manually.
        giveInitialReinforcement(currentPlayer);
        controller.startGameLoop();
    }

    public void attackCommand(int unitCount, Region baseRegion, Region target) {
        map.updateRegion(target, unitCount, baseRegion.getOwner());
        update();
    }

    public void update() {
        for (Player p : players) {
            p.update(map);
        }
    }

    public void addPlayer(Player p) {
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
