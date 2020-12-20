package application;

import application.controller.GameController;
import application.winstrategy.DestroyPlayerWin;
import application.winstrategy.DominantPlayerWin;
import application.winstrategy.TwoContinentWin;
import application.winstrategy.WorldDomination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Match implements Serializable {

    private static final double HARD_TROOP_MULTIPLIER = 1000;
    private static final double NORMAL_TROOP_MULTIPLIER = 1;
    private static final double EASY_TROOP_MULTIPLIER = 0.1;

    private final int[] troopPerPlayer = {0, 0, 40, 35, 30, 25, 20, 15};

    private int round;
    private int maxRound;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private HashMap<Faction, Boolean> availableFactions;
    private Map map;
    private GameController controller;
    private int numberOfCardTrades;
    //constructor
    public Match(int maxTurnTime){
        this.round = 0;
        this.maxRound = 100;
        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.availableFactions = new HashMap<>();
        this.map = null;
        this.controller = new GameController(this, maxTurnTime);
        numberOfCardTrades = 0;
    }


    //isMatcOver method returns true if match is over, false if match is continues
    public boolean isMatchOver(){
           for(Player p: players){
               if(p.checkWin())
                   return true;
           }
           return false;
    }

    public Player winner(){
        for(Player p: players){
            if(p.checkWin())
                return p;
        }
        return null;
    }

    //skips to the next player in line
    public void nextTurn() {
        currentPlayer.setTakenTradeCardAlready(false);

        if (round < maxRound) {
            round++;
            do {
                currentPlayer = players.get(round % players.size());
            }while(!currentPlayer.isAlive());
        }
        giveInitialReinforcement(currentPlayer);
    }

    public void giveInitialReinforcement(Player player)
    {
        int reinforcementsFromRegions;
        if (player.getRegions().size()/ 3 > 3){
            reinforcementsFromRegions = player.getRegions().size() / 3;
            reinforcementsFromRegions += (int) (reinforcementsFromRegions * player.getFaction().getTurnBonus());
        }
        else{
            reinforcementsFromRegions = 3;
            reinforcementsFromRegions += (int) (reinforcementsFromRegions * player.getFaction().getTurnBonus());
        }

        //TODO
        int reinforcementsFromContinents = player.checkForContinentBonuses();
        reinforcementsFromContinents += (int) (reinforcementsFromContinents * player.getFaction().getContinentalBonus());

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

    public void initialize(int AILevel){
        int numPlayers = players.size();
        int numRegions = map.getMapRegionCount();

        List<Region> available = new ArrayList<>();
        int[] remainingTroops = new int[numPlayers];

        for (int i = 0; i < numPlayers; i++)
        {
            remainingTroops[i] = troopPerPlayer[numPlayers];
            remainingTroops[i] += (int) (remainingTroops[i] * players.get(i).getFaction().getStartingBonus());

            if (players.get(i).isAI())
            {
                if (AILevel == 1) remainingTroops[i] = (int) (remainingTroops[i] * EASY_TROOP_MULTIPLIER);
                else if (AILevel == 2) remainingTroops[i] = (int) (remainingTroops[i] * NORMAL_TROOP_MULTIPLIER);
                else if (AILevel == 3) remainingTroops[i] = (int) (remainingTroops[i] * HARD_TROOP_MULTIPLIER);
            }
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
                randomRegionIndex = (int)(Math.random() * players.get(i).getRegions().size());
                Region randomRegion = players.get(i).getRegions().get(randomRegionIndex);
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

    public void giveTradeCard(){
        System.out.println("kart verdim");
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

    public int getTotalCardTrades(){

        return numberOfCardTrades;
    }

    public void increaseNumberOfTrades(){
        numberOfCardTrades++;
    }

    public void assignMissionSecretMission(){
        for (int i = 0; i < players.size(); i++) {
            int random =  (int)(Math.random() * 3);
            switch (random){
                case 0:
                    players.get(i).setMission(new Mission(new DestroyPlayerWin(players.get(i))));
                    break;
                case 1:
                    players.get(i).setMission(new Mission(new TwoContinentWin()));
                    break;
                case 2:
                    players.get(i).setMission(new Mission(new DominantPlayerWin()));
                    break;
            }
        }


    }

    public void assignMissionWorldDomination() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setMission(new Mission(new WorldDomination()));
        }

    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
}
