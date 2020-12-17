package application;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {


    private int maxTurnTime;
                                                                                // Where should we checked the maxTurnTime and the actual game time.
                                                                                // Also we can use AnimationTimer class of java instead of the timer class.
    private Timer turnTimer;
    private BattleManager battleManager;
    private StateManager stateManager;

    // Timer limits each player. It must be given as a attribute to the constructor.
    public GameController(int maxTurnTime, BattleManager battleManager,StateManager stateManager){
        turnTimer = new Timer();
        this.maxTurnTime = maxTurnTime;
        this.battleManager = battleManager;
        this.stateManager = stateManager;
    }
    public void startRound(){
        turnTimer.schedule(endTurn(), maxTurnTime*1000);
    }

    // changes the state to the next state.
    // end Turn because of the time limit.

    public TimerTask endTurn(){

        stateManager.changeState();
        return null;
    }

    public void battle(){


    }
}
