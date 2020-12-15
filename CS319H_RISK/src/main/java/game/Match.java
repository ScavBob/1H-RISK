public class Match{
    private int round;
    private int maxRound;
    private ArrayList<Player> players;
    private ArrayList<Mission> missionList;
    private HashMap <Faction,boolean> availableFactions;
    private Map map;
    private GameController controller;

    public void nextTurn();
}