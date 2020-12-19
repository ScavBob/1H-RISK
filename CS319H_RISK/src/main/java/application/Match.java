package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Match implements Serializable {

    private final int[] troopPerPlayer = {0, 0, 40, 35, 30, 25, 20, 15};

    private int round;
    private int maxRound;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private ArrayList<Mission> missionList;
    private HashMap<Faction, Boolean> availableFactions;
    private Map map;
    private GameController controller;
    private int numberOfCardTrades;
    //constructor
    public Match(int maxTurnTime){
        this.round = 0;
        this.maxRound = 100;
        this.players = new ArrayList<>();
        this.missionList = new ArrayList<>();
        this.currentPlayer = null;
        this.availableFactions = new HashMap<>();
        this.map = null;
        this.controller = new GameController(this, maxTurnTime);
        numberOfCardTrades = 0;
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
        int reinforcementsFromRegions;
        if (player.getRegions().size()/ 3 > 3){
            reinforcementsFromRegions = player.getRegions().size() / 3;
        }
        else{
            reinforcementsFromRegions = 3;
        }

        //TODO
        int reinforcementsFromContinents = player.checkForContinentBonuses();
        System.out.println(reinforcementsFromContinents);
        player.setAvailableReinforcements(reinforcementsFromRegions + reinforcementsFromContinents);
    }

    public int getRemainingTime()
    {
        return controller.getRemainingTime();
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
        int numPlayers = players.size();
        int numRegions = map.getMapRegionCount();

        List<Region> available = new ArrayList<>();
        int[] remainingTroops = new int[numPlayers];

        for (int i = 0; i < numPlayers; i++)
        {
            remainingTroops[i] = troopPerPlayer[numPlayers];
        }

        for (int i = 0; i < numRegions; i++)
        {
            available.add(map.getRegionList()[i]);
        }

        //Give everyone at least one region.
        for (int i = 0; i < numPlayers; i++)
        {
            int randomRegion = (int)(Math.random() * available.size());
            players.get(i).addRegion(available.get(randomRegion));
            available.remove(randomRegion);
        }

        while (available.size() > 0)
        {
            int randomPlayer = (int)(Math.random() * numPlayers);
            players.get(randomPlayer).addRegion(available.get(0));
            available.remove(0);
        }

        update();

        for (int i = 0; i < numPlayers; i++)
        {
            List<Region> playerRegions = players.get(i).getRegions();
            for (int j = 0; j < playerRegions.size(); j++)
            {
                if (remainingTroops[i] == 0) break;
                else
                {
                    playerRegions.get(j).setUnitCount(1);
                    remainingTroops[i]--;
                }
            }
        }

        int randomRegionIndex;
        for (int i = 0; i < numPlayers; i++)
        {
            while (remainingTroops[i] != 0)
            {
                randomRegionIndex = (int)(Math.random() * numRegions);
                Region randomRegion = map.getRegionList()[randomRegionIndex];
                randomRegion.setUnitCount(randomRegion.getUnitCount() + 1);
                remainingTroops[i]--;
            }
        }

        update();

        //The first player cannot get the initial reinforcements from nextTurn() method, so we give it here manually.
        giveInitialReinforcement(currentPlayer);
        startGameLoop();
    }

    public void startGameLoop()
    {
        controller.startGameLoop();
    }

    public int getCurrentPhase()
    {
        return controller.getCurrentPhase();
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

    private void giveTradeCard(){
        int chance = (int)(Math.random()*100);
        Card c;
        if(chance < 30)
            c = new Card(getRandomRegion(),0);
        else if(chance < 60)
            c = new Card(getRandomRegion(),1);
        else if(chance < 90)
            c = new Card((getRandomRegion()),2);
        else
            c = new Card(getRandomRegion(),3);
        currentPlayer.addTradeCard(c);
    }

    private Region getRandomRegion(){
        int loc = (int)(Math.random()*map.getRegionList().length);
        return map.getRegionList()[loc];
    }

    public int getTotalTrades(){
        return numberOfCardTrades;
    }

    public void increaseNumberOfTrades(){
        numberOfCardTrades++;
    }
}
