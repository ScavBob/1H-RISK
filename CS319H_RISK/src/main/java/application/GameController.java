package application;

import game.Game;
import managers.InputManager;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    public static final int ARMY_PLACEMENT_PHASE = 0;
    public static final int ATTACK_PHASE = 1;
    public static final int FORTIFY_PHASE = 2;

    private InputManager inputManager;
    private int maxTurnTime;
                                                                                // Where should we checked the maxTurnTime and the actual game time.
                                                                                // Also we can use AnimationTimer class of java instead of the timer class.
    private Timer turnTimer;
    private BattleManager battleManager;
    private StateManager stateManager;

    public GameController(){
        turnTimer = new Timer();
        inputManager = Game.getInstance().getGameManager().getInputManager();
        this.maxTurnTime = 1000;
        this.battleManager = new BattleManager();
        this.stateManager = new StateManager();
    }

    // Timer limits each player. It must be given as a attribute to the constructor.
    public GameController(int maxTurnTime, BattleManager battleManager,StateManager stateManager){
        turnTimer = new Timer();
        this.maxTurnTime = maxTurnTime;
        this.battleManager = battleManager;
        this.stateManager = stateManager;
    }

    void startGameLoop()
    {
        int currentPhase;
        currentPhase = stateManager.getPhase();

        //inputManager.awaitPlayerAction(this, currentPhase);
    }



    public void startRound(){
        turnTimer.schedule(endTurn(), maxTurnTime*1000);
    }

    // changes the state to the next state.
    // end Turn because of the time limit.

    public TimerTask endTurn(){

        //stateManager.changeState();
        return null;
    }

    public void battle(){


    }
}
